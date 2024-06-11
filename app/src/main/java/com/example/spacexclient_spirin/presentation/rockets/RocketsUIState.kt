package com.example.spacexclient_spirin.presentation.rockets

import android.text.SpannableString


data class RocketsUIState(
    val rocketId: String,
    val rocketName: String,
    val parameters: List<RocketsParametersUI>,
    val firstFlight: String,
    val country: String,
    val costPerLaunch: String,
    val firstStage: RocketsEntityStageInfoUI,
    val secondStage: RocketsEntityStageInfoUI,
    val flickrImages: List<String>
)

data class RocketsParametersUI(val value: String, val unit: String)

data class RocketsEntityStageInfoUI(
    val engines: String,
    val fuelAmount: SpannableString,
    val burnTime: SpannableString
)