package com.example.spacexclient_spirin.di.modules

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ResourcesModule(private val resources: Resources) {

    @Provides
    @Singleton
    fun provideResources(): Resources = resources

}