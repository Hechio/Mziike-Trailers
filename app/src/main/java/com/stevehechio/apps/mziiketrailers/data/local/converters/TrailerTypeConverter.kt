package com.stevehechio.apps.mziiketrailers.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stevehechio.apps.mziiketrailers.data.local.entities.Trailer

/**
 * Created by stevehechio on 10/23/21
 */
object TrailerTypeConverter {
    @TypeConverter
    fun fromStringToTrailer(value: String?): Trailer {
        val listType = object : TypeToken<Trailer?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromTrailerToString(list: Trailer?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}