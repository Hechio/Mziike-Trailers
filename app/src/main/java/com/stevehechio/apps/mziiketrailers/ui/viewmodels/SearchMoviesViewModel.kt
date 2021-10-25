package com.stevehechio.apps.mziiketrailers.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.stevehechio.apps.mziiketrailers.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

/**
 * Created by stevehechio on 10/23/21
 */
@HiltViewModel
@ExperimentalCoroutinesApi
class SearchMoviesViewModel @Inject constructor(private val searchRepository: SearchRepository)
    : ViewModel(){
    private val queryExpression: MutableLiveData<String?> = MutableLiveData()


    private val movieItemFlow = queryExpression.asFlow().flatMapLatest { search ->
        if (search != null) searchRepository.getMutableLiveData(search) else emptyFlow()
    }

    val movieList = movieItemFlow.asLiveData()

    fun getMoviesLists(query: String?){
        queryExpression.value = query
    }

    override fun onCleared() {
        super.onCleared()
        searchRepository.clear()
    }
}