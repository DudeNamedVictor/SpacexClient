package com.example.spacexclient_spirin.domain.entities


data class LaunchesEntity(
    val rocketId: String,
    val missionName: String,
    val launchDateUnix: Long,
    val isSuccessLaunch: Boolean
)