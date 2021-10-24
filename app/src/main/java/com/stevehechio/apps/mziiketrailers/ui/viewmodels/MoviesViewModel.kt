package com.stevehechio.apps.mziiketrailers.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.repository.FetchMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by stevehechio on 10/23/21
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: FetchMoviesRepository):
    BaseViewModel() {
    private val moviesLiveData: MutableLiveData<Resource<List<MoviesEntity>>> =
        MutableLiveData<Resource<List<MoviesEntity>>>()

    fun getMoviesLiveData(): MutableLiveData<Resource<List<MoviesEntity>>> {
        return moviesLiveData
    }

    fun fetchMovies(){
        addToDisposable(moviesRepository.fetchMovies().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                moviesLiveData.postValue(it)
                Log.v("Home VM Success", "Success Execution! $it")
            },{
                Log.v("Home VM error", "Success Execution! $it")
            }) )
    }

}