package com.example.spacexclient_spirin.di.modules

import com.example.spacexclient_spirin.domain.mappers.LaunchesMapper
import com.example.spacexclient_spirin.domain.mappers.RocketsDBMapper
import com.example.spacexclient_spirin.domain.mappers.RocketsMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MappersModule {

    @Singleton
    @Provides
    fun provideRocketsMapper(): RocketsMapper = RocketsMapper()

    @Singleton
    @Provides
    fun provideRocketsDBMapper(): RocketsDBMapper = RocketsDBMapper()

    @Singleton
    @Provides
    fun provideLaunchesMapper(): LaunchesMapper = LaunchesMapper()

}