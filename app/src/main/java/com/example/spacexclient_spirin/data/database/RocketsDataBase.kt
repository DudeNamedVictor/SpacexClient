package com.example.spacexclient_spirin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spacexclient_spirin.data.database.models.RocketModelDB


@Database(entities = [RocketModelDB::class], version = 1, exportSchema = false)
abstract class RocketsDataBase: RoomDatabase() {

    abstract fun rocketsDao() : RocketsDao

}