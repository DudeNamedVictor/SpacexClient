package com.example.spacexclient_spirin.presentation.launches

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.databinding.LaunchesFragmentLayoutBinding
import com.example.spacexclient_spirin.presentation.base.BaseFragment
import com.example.spacexclient_spirin.presentation.base.BaseViewModel
import com.example.spacexclient_spirin.utils.appComponent
import kotlinx.coroutines.launch


class LaunchesFragment :
    BaseFragment<LaunchesFragmentLayoutBinding>(LaunchesFragmentLayoutBinding::inflate) {

    private val viewModel: LaunchesViewModel by viewModels { appComponent().launchesViewModelFactory() }
    private var launchesAdapter: LaunchesAdapter? = null
    private val rocketName by lazy {
        arguments?.getString(ROCKET_NAME) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLaunches(arguments?.getString(ROCKET_ID) ?: "")
        initListeners()
    }

    override fun initView() {
        binding.back.setOnClickListener { findNavController().popBackStack() }
        binding.missionName.text = rocketName
        launchesAdapter = LaunchesAdapter()
        binding.launches.layoutManager =
            LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        binding.launches.adapter = launchesAdapter
    }

    private fun initListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { handleStateChange(it) }
            }
        }
    }

    private fun handleStateChange(state: BaseViewModel.ScreenState) {
        when (state) {
            is BaseViewModel.ScreenState.Success<*> -> {
                val launches = state.list.filterIsInstance<LaunchesUIState>()
                if (launches.isNotEmpty()) {
                    launchesAdapter?.submitList(launches)
                } else {
                    showError()
                }
                showProgress(false)
            }
            is BaseViewModel.ScreenState.Loading -> {
                showProgress(true)
            }
            is BaseViewModel.ScreenState.Error -> {
                findNavController().navigate(R.id.errorFragment)
            }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.launches.isVisible = !show
        binding.progress.isVisible = show
    }

    private fun showError() {
        binding.launches.isVisible = false
        binding.error.isVisible = true
    }

    companion object {
        const val ROCKET_ID = "rocketId"
        const val ROCKET_NAME = "rocketName"
    }

}