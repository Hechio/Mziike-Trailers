package com.stevehechio.apps.mziiketrailers.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.stevehechio.apps.mziiketrailers.data.local.converters.ActorsListTypeConvertor
import com.stevehechio.apps.mziiketrailers.data.local.converters.TrailerTypeConverter
import com.stevehechio.apps.mziiketrailers.utils.AppConstants
import java.io.Serializable

/**
 * Created by stevehechio on 10/20/21
 */

//use endpoint https://imdb-api.com/en/API/Title/k_vmay87jm/tt1375666/Trailer
@Entity(tableName = AppConstants.TABLE_NAME)
data class MoviesEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val year: String,
    val image: String,
    val plot: String,
    val genres: String,

    @TypeConverters(ActorsListTypeConvertor::class)
    val actorList: List<Actor>,
    val imDbRating: String,

    @TypeConverters(TrailerTypeConverter::class)
    val trailer: Trailer,
    var category: String

): Serializable


data class Actor(val image: String, val name: String): Serializable
data class Trailer(val thumbnailUrl: String, val link: String, val linkEmbed: String): Serializable