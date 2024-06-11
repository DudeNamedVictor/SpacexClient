package com.example.spacexclient_spirin.domain.repository

import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import kotlinx.coroutines.flow.Flow


interface RocketsRepository {

    suspend fun getRockets(): Flow<List<RocketsEntity>>

}