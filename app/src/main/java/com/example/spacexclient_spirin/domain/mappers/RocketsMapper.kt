package com.example.spacexclient_spirin.domain.mappers

import com.example.spacexclient_spirin.data.models.response.RocketsResponse
import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import com.example.spacexclient_spirin.domain.entities.RocketsEntityStageInfo
import com.example.spacexclient_spirin.domain.entities.RocketsParameters


class RocketsMapper {

    fun mappingRequest(response: RocketsResponse): RocketsEntity  {
       return RocketsEntity(
            rocketId = response.rocketId,
            rocketName = response.rocketName,
            parameters = mappingParameters(response),
            firstFlight = response.firstFlight,
            country = response.country,
            costPerLaunch = response.costPerLaunch,
            firstStage = RocketsEntityStageInfo(
                response.firstStage.engines.toString(),
                response.firstStage.fuelAmountTons.toString(),
                response.firstStage.burnTimeSec.toString()
            ),
            secondStage = RocketsEntityStageInfo(
                response.secondStage.engines.toString(),
                response.secondStage.fuelAmountTons.toString(),
                response.secondStage.burnTimeSec.toString()
            ),
            flickrImages = response.flickrImages
        )
    }

    private fun mappingParameters(data: RocketsResponse): List<RocketsParameters> {
        val parameters = mutableListOf<RocketsParameters>()
        parameters.add(RocketsParameters(data.height.meters.toString(), data.height.feet.toString()))
        parameters.add(RocketsParameters(data.diameter.meters.toString(), data.diameter.feet.toString()))
        parameters.add(RocketsParameters(data.mass.kg.toString(), data.mass.lb.toString()))
        parameters.add(RocketsParameters(data.payloadWeights[0].kg.toString(), data.payloadWeights[0].lb.toString()))

        return parameters
    }

}