package com.stevehechio.apps.mziiketrailers.utils

/**
 * Created by stevehechio on 10/25/21
 */
object RatingConvertor {
    fun getRating(rating: String?): String{
        if (rating.isNullOrEmpty()) return "0.0"
        val doubleRating = rating.toDouble()
        val overFiveRating = (5.0 * doubleRating)/10.0
        return overFiveRating.toString()
    }
}