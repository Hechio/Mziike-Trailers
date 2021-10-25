package com.stevehechio.apps.mziiketrailers.data.remote.api

import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.remote.model.MovieSearchResponse
import com.stevehechio.apps.mziiketrailers.data.remote.model.MoviesCategoryApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by stevehechio on 10/23/21
 */
//top250movies //top250tv mostpopular tv movies in theaters comming soon
interface MovieApiService {
    @GET("Top250Movies/{api_key}")
    fun fetch250Movies(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>
    @GET("Top250TVs/{api_key}")
    fun fetch250TVs(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>
    @GET("MostPopularMovies/{api_key}")
    fun fetchMostPopularMovies(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>
    @GET("MostPopularTVs/{api_key}")
    fun fetchMostPopularTVs(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>
    @GET("InTheaters/{api_key}")
    fun fetchInTheaters(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>
    @GET("ComingSoon/{api_key}")
    fun fetchComingSoonMovies(@Path("api_key") apiKey: String)
        : Observable<MoviesCategoryApiResponse>


    @GET("Title/{api_key}/{movie_id}/Trailer")
    fun fetchMovieById(@Path("movie_id") movieId: String,
                       @Path("api_key") apiKey: String)
        : Observable<MoviesEntity>

    ///SearchTitle/k_vmay87jm/inception 2010
    @GET("SearchTitle/{api_key}/{expression}")
    fun searchForMovie(@Path("expression") expression: String,
                       @Path("api_key") apiKey: String)
        : Observable<MovieSearchResponse>


}