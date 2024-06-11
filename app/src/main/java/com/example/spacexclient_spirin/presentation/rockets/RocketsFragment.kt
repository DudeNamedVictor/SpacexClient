package com.example.spacexclient_spirin.presentation.rockets

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.databinding.RocketsFragmentLayoutBinding
import com.example.spacexclient_spirin.presentation.base.BaseFragment
import com.example.spacexclient_spirin.presentation.base.BaseViewModel
import com.example.spacexclient_spirin.presentation.launches.LaunchesFragment.Companion.ROCKET_ID
import com.example.spacexclient_spirin.presentation.launches.LaunchesFragment.Companion.ROCKET_NAME
import com.example.spacexclient_spirin.utils.appComponent
import com.example.spacexclient_spirin.utils.dataStore
import kotlinx.coroutines.launch


class RocketsFragment :
    BaseFragment<RocketsFragmentLayoutBinding>(RocketsFragmentLayoutBinding::inflate) {

    private val viewModel: RocketsViewModel by viewModels { appComponent().rocketViewModelFactory() }
    private var rocketsAdapter: RocketsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        viewModel.prepareScreen(requireContext().dataStore)
    }

    override fun initView() {
        rocketsAdapter = RocketsAdapter(
            { findNavController().navigate(R.id.settingsFragment) },
            { rocketId, rocketName ->
                findNavController().navigate(
                    R.id.launchesFragment,
                    bundleOf(ROCKET_ID to rocketId, ROCKET_NAME to rocketName)
                )
            }
        )
        binding.container.adapter = rocketsAdapter
        binding.wormDotsIndicator.attachTo(binding.container)
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
                rocketsAdapter?.submitList(state.list.filterIsInstance<RocketsUIState>())
                showProgress(false)
                binding.wormDotsIndicator.refreshDots()
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
        binding.container.isVisible = !show
        binding.wormDotsIndicator.isVisible = !show
        binding.progress.isVisible = show
    }

}