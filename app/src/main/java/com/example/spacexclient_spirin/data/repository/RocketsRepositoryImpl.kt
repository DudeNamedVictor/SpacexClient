package com.example.spacexclient_spirin.data.repository

import com.example.spacexclient_spirin.data.database.RocketsDao
import com.example.spacexclient_spirin.data.network.NetworkApi
import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import com.example.spacexclient_spirin.domain.mappers.RocketsDBMapper
import com.example.spacexclient_spirin.domain.mappers.RocketsMapper
import com.example.spacexclient_spirin.domain.repository.RocketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RocketsRepositoryImpl @Inject constructor(
    private val rocketsDao: RocketsDao,
    private val networkApi: NetworkApi,
    private val rocketsMapper: RocketsMapper,
    private val rocketsDBMapper: RocketsDBMapper
) : RocketsRepository {

    override suspend fun getRockets(): Flow<List<RocketsEntity>> = flow {
        val localData = getLocalData().first()
        emit(localData)
        rocketsDao.updateRockets(rocketsDBMapper.mappingToDBModel(getRocketsFromServer()))
        emitAll(getLocalData())
    }

//        inline fun <ResultT, RequestT> makeNetworkCall(
//        crossinline getLocalData: suspend () -> Flow<ResultT>,
//        crossinline fetchData: suspend () -> RequestT,
//        crossinline saveFetchedData: suspend (RequestT) -> Unit,
//    ) = flow {
//        val data = getLocalData().first()
//        emit(LoadState.Success(data))
//
//        val flow = try {
//            saveFetchedData(fetchData())
//            getLocalData().map { LoadState.Success(it) }
//        } catch (throwable: Throwable) {
//            getLocalData().map {
//                LoadState.Error(throwable, it)
//            }
//        }
//
//        emitAll(flow)
//    }

    private fun getLocalData(): Flow<List<RocketsEntity>> = rocketsDao.getRockets().map {
        rocketsDBMapper.mappingToEntity(it)
    }

    private suspend fun getRocketsFromServer(): List<RocketsEntity> {
        return networkApi.getRockets().map {
            rocketsMapper.mappingRequest(it)
        }
    }

}