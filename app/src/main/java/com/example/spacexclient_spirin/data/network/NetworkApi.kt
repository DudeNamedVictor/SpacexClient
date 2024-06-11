package com.example.spacexclient_spirin.data.network

import com.example.spacexclient_spirin.data.models.response.LaunchesResponse
import com.example.spacexclient_spirin.data.models.response.RocketsResponse
import retrofit2.http.GET


interface NetworkApi {

    @GET("/v3/rockets")
    suspend fun getRockets(): List<RocketsResponse>

    @GET("/v3/launches")
    suspend fun getLaunches(): List<LaunchesResponse>

}