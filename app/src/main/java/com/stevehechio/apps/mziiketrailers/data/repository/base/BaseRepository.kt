package com.stevehechio.apps.mziiketrailers.data.repository.base

import io.reactivex.disposables.Disposable

/**
 * Created by stevehechio on 10/23/21
 */
interface BaseRepository {
    fun addDisposable(disposable: Disposable)

    fun clear()
}