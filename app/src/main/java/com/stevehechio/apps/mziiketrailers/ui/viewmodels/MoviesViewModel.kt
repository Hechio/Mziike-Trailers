package com.stevehechio.apps.mziiketrailers.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.data.repository.FetchMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by stevehechio on 10/23/21
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: FetchMoviesRepository):
    ViewModel() {
    private val moviesLiveData: MutableLiveData<Resource<List<MoviesEntity>>> =
        MutableLiveData<Resource<List<MoviesEntity>>>()

    init {
        moviesRepository.fetchMovies()
        fetchMovies()
    }

    fun getMoviesLiveData(): MutableLiveData<Resource<List<MoviesEntity>>> {
        return moviesLiveData
    }

    private fun fetchMovies(){
        moviesLiveData.value = moviesRepository.result.value
    }

}