package com.example.spacexclient_spirin.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.databinding.SettingsFragmentLayoutBinding
import com.example.spacexclient_spirin.presentation.base.BaseFragment
import com.example.spacexclient_spirin.presentation.settings.SettingsViewModel.Companion.DIAMETER
import com.example.spacexclient_spirin.presentation.settings.SettingsViewModel.Companion.HEIGHT
import com.example.spacexclient_spirin.presentation.settings.SettingsViewModel.Companion.MASS
import com.example.spacexclient_spirin.presentation.settings.SettingsViewModel.Companion.PAYLOAD_WEIGHTS
import com.example.spacexclient_spirin.utils.dataStore
import kotlinx.coroutines.launch


class SettingsFragment :
    BaseFragment<SettingsFragmentLayoutBinding>(SettingsFragmentLayoutBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        viewModel.getInitState(requireContext().resources, requireContext().dataStore)
    }

    override fun initView() {
        binding.close.setOnClickListener { findNavController().popBackStack() }
        binding.heightSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                val state = if (isChecked) getString(R.string.ft) else getString(R.string.m)
                viewModel.updateDataStoreField(requireContext().dataStore, HEIGHT, state)
            }
        }
        binding.diameterSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                val state = if (isChecked) getString(R.string.ft) else getString(R.string.m)
                viewModel.updateDataStoreField(requireContext().dataStore, DIAMETER, state)
            }
        }
        binding.massSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                val state = if (isChecked) getString(R.string.lb) else getString(R.string.kg)
                viewModel.updateDataStoreField(requireContext().dataStore, MASS, state)
            }
        }
        binding.payloadWeightsSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                val state = if (isChecked) getString(R.string.lb) else getString(R.string.kg)
                viewModel.updateDataStoreField(requireContext().dataStore, PAYLOAD_WEIGHTS, state)
            }
        }
    }

    private fun initListeners() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { handleStateChange(it) }
            }
        }
    }

    private fun handleStateChange(state: SettingsViewModel.SwitchesState) {
        binding.heightSwitch.isChecked = updateParameter(state.height)
        binding.diameterSwitch.isChecked = updateParameter(state.diameter)
        binding.massSwitch.isChecked = updateParameter(state.mass)
        binding.payloadWeightsSwitch.isChecked = updateParameter(state.payloadWeights)
    }

    private fun updateParameter(state: String) =
        !(state == getString(R.string.m) || state == getString(R.string.kg))

}