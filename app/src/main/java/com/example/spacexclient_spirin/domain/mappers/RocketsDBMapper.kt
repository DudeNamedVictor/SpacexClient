package com.example.spacexclient_spirin.domain.mappers

import com.example.spacexclient_spirin.data.database.models.RocketModelDB
import com.example.spacexclient_spirin.data.database.models.StageInfoDB
import com.example.spacexclient_spirin.data.database.models.UnitsDB
import com.example.spacexclient_spirin.data.database.models.UnitsOfMassDB
import com.example.spacexclient_spirin.domain.entities.RocketsEntity
import com.example.spacexclient_spirin.domain.entities.RocketsEntityStageInfo
import com.example.spacexclient_spirin.domain.entities.RocketsParameters


class RocketsDBMapper {

    fun mappingToEntity(list: List<RocketModelDB>): List<RocketsEntity> = list.map {
        RocketsEntity(
            rocketId = it.rocketId,
            rocketName = it.rocketName,
            parameters = mappingParameters(it),
            firstFlight = it.firstFlight,
            country = it.country,
            costPerLaunch = it.costPerLaunch,
            firstStage = RocketsEntityStageInfo(
                it.firstStage.engines.toString(),
                it.firstStage.fuelAmountTons.toString(),
                it.firstStage.burnTimeSec.toString()
            ),
            secondStage = RocketsEntityStageInfo(
                it.secondStage.engines.toString(),
                it.secondStage.fuelAmountTons.toString(),
                it.secondStage.burnTimeSec.toString()
            ),
            flickrImages = it.flickrImages
        )
    }

    fun mappingToDBModel(list: List<RocketsEntity>): List<RocketModelDB> = list.map {
        RocketModelDB(
            rocketId = it.rocketId,
            rocketName = it.rocketName,
            height = UnitsDB(
                meters = it.parameters[0].firstValue.toDouble(),
                feet = it.parameters[0].secondValue.toDouble()
            ),
            diameter = UnitsDB(
                meters = it.parameters[1].firstValue.toDouble(),
                feet = it.parameters[1].secondValue.toDouble()
            ),
            mass = UnitsOfMassDB(
                kg = it.parameters[2].firstValue.toDouble(),
                lb = it.parameters[2].secondValue.toDouble()
            ),
//            parameters = mappingParameters(it),
            firstFlight = it.firstFlight,
            country = it.country,
            costPerLaunch = it.costPerLaunch,
            firstStage = StageInfoDB(
                it.firstStage.engines.toInt(),
                it.firstStage.fuelAmount.toDouble(),
                it.firstStage.burnTime.toInt()
            ),
            secondStage = StageInfoDB(
                it.secondStage.engines.toInt(),
                it.secondStage.fuelAmount.toDouble(),
                it.secondStage.burnTime.toInt()
            ),
            flickrImages = it.flickrImages
        )
    }

    private fun mappingParameters(data: RocketModelDB): List<RocketsParameters> {
        val parameters = mutableListOf<RocketsParameters>()
        parameters.add(RocketsParameters(data.height.meters.toString(), data.height.feet.toString()))
        parameters.add(RocketsParameters(data.diameter.meters.toString(), data.diameter.feet.toString()))
        parameters.add(RocketsParameters(data.mass.kg.toString(), data.mass.lb.toString()))
//        parameters.add(RocketsParameters(data.payloadWeights[0].kg.toString(), data.payloadWeights[0].lb.toString()))

        return parameters
    }

}