package com.stevehechio.apps.mziiketrailers.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.remote.api.MovieApiService
import com.stevehechio.apps.mziiketrailers.data.remote.model.MoviesSearch
import com.stevehechio.apps.mziiketrailers.data.repository.base.BaseRepository
import com.stevehechio.apps.mziiketrailers.utils.AppConstants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by stevehechio on 10/23/21
 */
class SearchRepository @Inject constructor(val movieApiService: MovieApiService): BaseRepository{
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val result = MutableLiveData<Resource<List<MoviesSearch>>>()

    fun getMutableLiveData(query: String): Flow<Resource<List<MoviesSearch>>>{
        addDisposable(movieApiService.searchForMovie(query,AppConstants.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onStartFetching() }
            .subscribe({response -> onSuccess(response.moviesList)},
                {onErrorOcurred(it)})
        )
        return result.asFlow()
    }

    private fun onErrorOcurred(it: Throwable) {
        result.value = Resource.Failure(it.toString())
    }

    private fun onSuccess(response: List<MoviesSearch>?) {
        result.value = Resource.Success(response)
    }

    private fun onStartFetching() {
        result.value = Resource.Loading()
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clear() {
       compositeDisposable.clear()
    }
}