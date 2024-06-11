package com.example.spacexclient_spirin.domain.mappers

import com.example.spacexclient_spirin.data.models.response.LaunchesResponse
import com.example.spacexclient_spirin.domain.entities.LaunchesEntity


class LaunchesMapper {

    fun mappingRequest(list: List<LaunchesResponse>): List<LaunchesEntity> = list.map {
        LaunchesEntity(
            rocketId = it.rocket.rocketId,
            missionName = it.missionName,
            launchDateUnix = it.launchDateUnix,
            isSuccessLaunch = it.launchSuccess
        )
    }

}