package com.stevehechio.apps.mziiketrailers.ui.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by stevehechio on 10/24/21
 */
open class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    fun addToDisposable(disposable: Disposable?) {
        compositeDisposable.remove(disposable!!)
        compositeDisposable.add(disposable)
    }

    private fun onStop() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        onStop()
    }
}
