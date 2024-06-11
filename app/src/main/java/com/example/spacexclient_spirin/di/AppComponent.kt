package com.example.spacexclient_spirin.di

import com.example.spacexclient_spirin.di.modules.DataBaseModule
import com.example.spacexclient_spirin.di.modules.MappersModule
import com.example.spacexclient_spirin.di.modules.NetworkModule
import com.example.spacexclient_spirin.di.modules.ResourcesModule
import com.example.spacexclient_spirin.presentation.launches.LaunchesViewModel
import com.example.spacexclient_spirin.presentation.rockets.RocketsViewModel
import dagger.Component
import javax.inject.Singleton


@Component(modules = [NetworkModule::class, DataBaseModule::class, MappersModule::class, ResourcesModule::class])
@Singleton
interface AppComponent {

    fun rocketViewModelFactory(): RocketsViewModel.RocketsViewModelFactory
    fun launchesViewModelFactory(): LaunchesViewModel.LaunchesViewModelFactory

}