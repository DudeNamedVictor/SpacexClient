package com.example.spacexclient_spirin.domain.usecases

import com.example.spacexclient_spirin.data.repository.RocketsRepositoryImpl
import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetRocketsUseCase @Inject constructor(private var rocketsRepositoryImpl: RocketsRepositoryImpl) {

    suspend fun invoke(): Flow<List<RocketsEntity>> = rocketsRepositoryImpl.getRockets()

}