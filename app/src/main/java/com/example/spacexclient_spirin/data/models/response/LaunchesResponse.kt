package com.example.spacexclient_spirin.data.models.response

import com.google.gson.annotations.SerializedName


data class LaunchesResponse(
    @SerializedName("rocket")
    val rocket: Rocket,
    @SerializedName("mission_name")
    val missionName: String,
    @SerializedName("launch_date_unix")
    val launchDateUnix: Long,
    @SerializedName("launch_success")
    val launchSuccess: Boolean
)

data class Rocket(@SerializedName("rocket_id") val rocketId: String)