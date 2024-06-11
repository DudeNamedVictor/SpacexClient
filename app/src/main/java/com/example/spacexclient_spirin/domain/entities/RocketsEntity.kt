package com.example.spacexclient_spirin.domain.entities


data class RocketsEntity(
    val rocketId: String,
    val rocketName: String,
    val parameters: List<RocketsParameters>,
    val firstFlight: String,
    val country: String,
    val costPerLaunch: Int,
    val firstStage: RocketsEntityStageInfo,
    val secondStage: RocketsEntityStageInfo,
    val flickrImages: List<String>
)

data class RocketsParameters(val firstValue: String, val secondValue: String)

data class RocketsEntityStageInfo(
    val engines: String,
    val fuelAmount: String,
    val burnTime: String
)