package com.example.spacexclient_spirin.domain.usecases

import com.example.spacexclient_spirin.data.repository.LaunchesRepositoryImpl
import javax.inject.Inject


class GetLaunchesUseCase @Inject constructor(private var launchesRepositoryImpl: LaunchesRepositoryImpl) {

    suspend fun invoke(rocketId: String) =
        runCatching { launchesRepositoryImpl.getLaunches().filter { it.rocketId == rocketId } }

}