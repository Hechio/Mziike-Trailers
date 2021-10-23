package com.stevehechio.apps.mziiketrailers.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by stevehechio on 10/23/21
 */
data class MovieSearchResponse(
    @SerializedName("results")
    @Expose
    val moviesList: List<MoviesSearch>,

    val errorMessage: String)

data class MoviesSearch(val id: String,val title: String,val image: String, val description: String)

