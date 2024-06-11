package com.example.spacexclient_spirin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spacexclient_spirin.databinding.MainActivityLayoutBinding


class MainActivity : AppCompatActivity() {

    private var binding: MainActivityLayoutBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

}