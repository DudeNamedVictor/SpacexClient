package com.example.spacexclient_spirin.presentation.error

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.spacexclient_spirin.R
import com.example.spacexclient_spirin.databinding.ErrorFragmentLayoutBinding
import com.example.spacexclient_spirin.presentation.base.BaseFragment
import com.example.spacexclient_spirin.utils.isNetworkConnected


class ErrorFragment :
    BaseFragment<ErrorFragmentLayoutBinding>(ErrorFragmentLayoutBinding::inflate) {

    override fun initView() {
        binding.back.setOnClickListener {
            if (requireContext().isNetworkConnected) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), R.string.error_message, Toast.LENGTH_LONG).show()
            }
        }
    }

}