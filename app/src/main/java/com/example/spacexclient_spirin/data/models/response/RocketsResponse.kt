package com.example.spacexclient_spirin.data.models.response

import com.google.gson.annotations.SerializedName


data class RocketsResponse(
    @SerializedName("rocket_id")
    val rocketId: String,
    @SerializedName("rocket_name")
    val rocketName: String,
    @SerializedName("height")
    val height: Units,
    @SerializedName("diameter")
    val diameter: Units,
    @SerializedName("mass")
    val mass: UnitsOfMass,
    @SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeights>,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @SerializedName("first_stage")
    val firstStage: StageInfo,
    @SerializedName("second_stage")
    val secondStage: StageInfo,
    @SerializedName("flickr_images")
    val flickrImages: List<String>
)

data class Units(
    @SerializedName("meters")
    val meters: Double,
    @SerializedName("feet")
    val feet: Double
)

data class UnitsOfMass(
    @SerializedName("kg")
    val kg: Double,
    @SerializedName("lb")
    val lb: Double
)

data class PayloadWeights(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("kg")
    val kg: Double,
    @SerializedName("lb")
    val lb: Double
)

data class StageInfo(
    @SerializedName("engines")
    val engines: Int,
    @SerializedName("fuel_amount_tons")
    val fuelAmountTons: Double,
    @SerializedName("burn_time_sec")
    val burnTimeSec: Int
)