package com.stevehechio.apps.mziiketrailers.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by stevehechio on 10/20/21
 */
@Entity
data class MoviesEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val image: String,
    val crew: String,
    val imDbRating: String

): Serializable