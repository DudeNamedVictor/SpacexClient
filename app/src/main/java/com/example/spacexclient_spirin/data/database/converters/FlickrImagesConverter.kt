package com.example.spacexclient_spirin.data.database.converters

import androidx.room.TypeConverter
import java.util.stream.Collectors


class FlickrImagesConverter {

    @TypeConverter
    fun fromFlickrImages(flickrImages: List<String>): String {
        return flickrImages.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toFlickrImages(data: String): List<String> {
        return listOf(*data.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
    }

}