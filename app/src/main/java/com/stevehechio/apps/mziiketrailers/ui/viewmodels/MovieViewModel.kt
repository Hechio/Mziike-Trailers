package com.stevehechio.apps.mziiketrailers.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.repository.GetMovieDetailsByIdRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by stevehechio on 10/25/21
 */

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMovieDetailsByIdRepo: GetMovieDetailsByIdRepo):
BaseViewModel(){
    private val moviesLiveData: MutableLiveData<Resource<MoviesEntity>> =
        MutableLiveData<Resource<MoviesEntity>>()

    fun getMoviesLiveData(): MutableLiveData<Resource<MoviesEntity>> {
        return moviesLiveData
    }

    fun fetchMovies(id: String){
        addToDisposable(getMovieDetailsByIdRepo.fetchMovie(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                moviesLiveData.postValue(it)
                Log.v("Detail VM Success", "Success Execution! $it")
            },{
                moviesLiveData.postValue(Resource.Failure(it.localizedMessage))
                Log.e("Detail VM error", "Success Execution! $it")
            }) )
    }
}