package com.example.spacexclient_spirin.data.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.spacexclient_spirin.data.database.converters.FlickrImagesConverter


@Entity(tableName = "rockets")
@TypeConverters(FlickrImagesConverter::class)
data class RocketModelDB(
    @PrimaryKey(autoGenerate = false)
    val rocketId: String,
    val rocketName: String,
    @Embedded(prefix = "height_")
    val height: UnitsDB,
    @Embedded(prefix = "diameter_")
    val diameter: UnitsDB,
    @Embedded(prefix = "mass_")
    val mass: UnitsOfMassDB,
//    @Ignore
//    val payloadWeights: List<PayloadWeightsDB>,
    val firstFlight: String,
    val country: String,
    val costPerLaunch: Int,
    @Embedded(prefix = "firstStage_")
    val firstStage: StageInfoDB,
    @Embedded(prefix = "secondStage_")
    val secondStage: StageInfoDB,
    val flickrImages: List<String>
)

data class UnitsDB(
    val meters: Double,
    val feet: Double
)

data class UnitsOfMassDB(
    val kg: Double,
    val lb: Double
)

data class PayloadWeightsDB(
    val id: String,
    val name: String,
    val kg: Double,
    val lb: Double
)

data class StageInfoDB(
    val engines: Int,
    val fuelAmountTons: Double,
    val burnTimeSec: Int
)
