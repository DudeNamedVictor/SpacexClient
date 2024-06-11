package com.example.spacexclient_spirin.di.modules

import android.app.Application
import androidx.room.Room
import com.example.spacexclient_spirin.data.database.RocketsDataBase
import com.example.spacexclient_spirin.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideRocketsDatabase() = Room.databaseBuilder(
        application, RocketsDataBase::class.java, DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideNoteDao(dataBase: RocketsDataBase) = dataBase.rocketsDao()

}