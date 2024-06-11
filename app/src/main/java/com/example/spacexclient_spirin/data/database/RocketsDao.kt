package com.example.spacexclient_spirin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacexclient_spirin.data.database.models.RocketModelDB
import kotlinx.coroutines.flow.Flow


@Dao
interface RocketsDao {

    @Query("Select * FROM rockets")
    fun getRockets(): Flow<List<RocketModelDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateRockets(rockets: List<RocketModelDB>)

}