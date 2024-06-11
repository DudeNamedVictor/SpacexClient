package com.example.spacexclient_spirin.presentation

import android.app.Application
import com.example.spacexclient_spirin.di.AppComponent
import com.example.spacexclient_spirin.di.DaggerAppComponent
import com.example.spacexclient_spirin.di.modules.DataBaseModule
import com.example.spacexclient_spirin.di.modules.ResourcesModule


class SpaceXApplication: Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .resourcesModule(ResourcesModule(this.resources))
            .dataBaseModule(DataBaseModule(this))
            .build()
    }

}