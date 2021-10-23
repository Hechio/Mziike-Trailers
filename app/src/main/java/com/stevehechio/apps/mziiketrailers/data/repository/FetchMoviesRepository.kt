package com.stevehechio.apps.mziiketrailers.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.dao.MovieDao
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.remote.api.MovieApiService
import com.stevehechio.apps.mziiketrailers.data.repository.base.BaseRepository
import com.stevehechio.apps.mziiketrailers.utils.AppConstants
import com.stevehechio.apps.mziiketrailers.utils.NetworkUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by stevehechio on 10/23/21
 */
class FetchMoviesRepository @Inject constructor(
    val application: Context,
    val movieDao: MovieDao,
    val movieApiService: MovieApiService
) : BaseRepository {

    private val compositeDisposable = CompositeDisposable()
    val result = MutableLiveData<Resource<List<MoviesEntity>>>()

    fun fetchMovies(){
        if (NetworkUtil.isNetworkAvailable(application)){
            getRemoteMovies(AppConstants.API_KEY)
        }else {
            getLocalData()
        }
    }

    private fun getRemoteMovies(apiKey: String) {

        val disposable = Observable.zip(
            movieApiService.fetch250Movies(apiKey),
            movieApiService.fetch250TVs(apiKey),
            movieApiService.fetchMostPopularMovies(apiKey),
            movieApiService.fetchComingSoonMovies(apiKey),
            movieApiService.fetchMostPopularTVs(apiKey),
            movieApiService.fetchInTheaters(apiKey),
            { top250Movies, top250TVs, topPopularMovies, topComingSoonMovies,topMostPopularMovies, topInTheaterMovies ->
                //movieDao.deleteAll()
                val list1 = top250Movies.moviesList
                val n = list1.size
                for (i in 0 until n) {
                    list1[i].category = "Top 250 Movies"
                }
                Log.i("movies res", "Success Execution! $top250Movies")
                Log.i("movies res", "Success Execution! $list1")
                movieDao.insertAll(list1)

                val list2 = top250TVs.moviesList
                val x = list2.size
                for (i in 0 until x) {
                    list2[i].category = "Top 250 Tv Shows"
                }
                movieDao.insertAll(list2)
                val list3 = topPopularMovies.moviesList
                val y = list3.size
                for (i in 0 until y) {
                    list3[i].category = "Most Popular Movies"
                }
                movieDao.insertAll(list3)
                val list4 = topComingSoonMovies.moviesList
                val z = list4.size
                for (i in 0 until z) {
                    list4[i].category = "Coming Soon"
                }
                movieDao.insertAll(list4)
                val list5 = topMostPopularMovies.moviesList
                val j = list5.size
                for (i in 0 until j) {
                    list5[i].category = "Most Popular Tv Shows"
                }
                movieDao.insertAll(list5)
                val list6 = topInTheaterMovies.moviesList
                val w = list6.size
                for (i in 0 until w) {
                    list6[i].category = "In Theaters"
                }

                movieDao.insertAll(list6)
            })
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .switchMap {
                movieDao.getAllMovies().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            }
            .doOnSubscribe { }
            .doOnTerminate { }
            .subscribe({
                onSuccess(it)
                Log.i("movies res", "Success Execution! $it")
            },{
                onErrorOcurred(it)
            })

        addDisposable(disposable)
    }
    private fun getLocalData(){
        val disposable = movieDao.getAllMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStartFetching() }
            .subscribe({
                onSuccess(it)
                Log.i("movies res", "Success Execution! $it")
            },{
                onErrorOcurred(it)
            })
        addDisposable(disposable)
    }
    private fun onErrorOcurred(it: Throwable) {
        result.postValue(Resource.Failure(it.toString()))
    }

    private fun onSuccess(response: List<MoviesEntity>?) {

        result.postValue(Resource.Success(response))
    }

    private fun onStartFetching() {
        result.postValue(Resource.Loading())
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}