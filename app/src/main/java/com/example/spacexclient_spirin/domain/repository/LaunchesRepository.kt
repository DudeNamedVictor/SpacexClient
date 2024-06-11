package com.example.spacexclient_spirin.domain.repository

import com.example.spacexclient_spirin.domain.entities.LaunchesEntity


interface LaunchesRepository {

    suspend fun getLaunches(): List<LaunchesEntity>

}