package com.stevehechio.apps.mziiketrailers.data.repository

import android.content.Context
import android.util.Log
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
 * Created by stevehechio on 10/25/21
 */
class GetMovieDetailsByIdRepo @Inject constructor(
    val application: Context,
    val movieDao: MovieDao,
    val movieApiService: MovieApiService
): BaseRepository {
    private val compositeDisposable = CompositeDisposable()

    fun fetchMovie(id: String): Observable<Resource<MoviesEntity>> {
        return if (NetworkUtil.isNetworkAvailable(application)){
            getRemoteMovie(id,AppConstants.API_KEY)
        }else {
            getLocalData(id)
        }
    }

    private fun getLocalData(id: String): Observable<Resource<MoviesEntity>> {
        return Observable.create <Resource<MoviesEntity>>()  { emiter ->
            emiter.onNext(Resource.Loading())
            val disposable = movieDao.getMovieById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    emiter.onNext( Resource.Success(it))
                    Log.i("movies res", "Success Execution! $it")
                }, {
                    emiter.onNext(Resource.Failure(it.toString()))
                    Log.i("movies res", "Success Execution! $it")
                })
            addDisposable(disposable)
        }
    }

    private fun getRemoteMovie(id: String, apiKey: String): Observable<Resource<MoviesEntity>> {
        return Observable.create <Resource<MoviesEntity>>(){emiter ->
            emiter.onNext(Resource.Loading())

            val disposable = movieApiService.fetchMovieById(id,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .switchMap { movieEntity ->
                   val  rows = movieDao.updateMovie(id, movieEntity.genres,movieEntity.plot,
                        movieEntity.trailer,movieEntity.actorList)

                    Observable.just(rows).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                }
                .switchMap {
                    movieDao.getMovieById(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                }
                .subscribe({ remoteResponse ->
                    emiter.onNext( Resource.Success(remoteResponse))
                    Log.i("movies res", "Success Execution! $remoteResponse")
                },{
                    emiter.onNext(Resource.Failure(it.toString()))
                    Log.i("movie error", "Success Execution! $it")
                })

            addDisposable(disposable)
        }
    }


    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clear() {
       compositeDisposable.clear()
    }
}