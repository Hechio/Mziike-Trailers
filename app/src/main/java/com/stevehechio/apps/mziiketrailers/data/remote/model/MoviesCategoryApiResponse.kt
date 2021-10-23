package com.stevehechio.apps.mziiketrailers.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import java.io.Serializable

/**
 * Created by stevehechio on 10/23/21
 */
data class MoviesCategoryApiResponse(
    @SerializedName("items")
    @Expose
    val moviesList: List<MoviesEntity>,

    val errorMessage: String
):Serializable
