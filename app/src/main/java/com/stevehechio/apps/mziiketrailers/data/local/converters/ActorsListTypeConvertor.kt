package com.stevehechio.apps.mziiketrailers.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stevehechio.apps.mziiketrailers.data.local.entities.Actor

/**
 * Created by stevehechio on 10/23/21
 */
object ActorsListTypeConvertor {
    @TypeConverter
    fun fromStringToActors(value: String?): List<Actor> {
        val listType = object : TypeToken<List<Actor>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromActorListToString(list: List<Actor>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}