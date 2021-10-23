package com.stevehechio.apps.mziiketrailers.data

/**
 * Created by stevehechio on 10/23/21
 */
open class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Failure<out T>(val cause: String?) : Resource<T>()
}