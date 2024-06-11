package com.example.spacexclient_spirin.data.repository

import com.example.spacexclient_spirin.data.network.NetworkApi
import com.example.spacexclient_spirin.domain.mappers.LaunchesMapper
import com.example.spacexclient_spirin.domain.repository.LaunchesRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LaunchesRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi,
    private val launchesMapper: LaunchesMapper
) : LaunchesRepository {

    override suspend fun getLaunches() = launchesMapper.mappingRequest(networkApi.getLaunches())

}