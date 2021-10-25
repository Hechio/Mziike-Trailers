package com.stevehechio.apps.mziiketrailers.utils

import android.view.View

/**
 * Created by stevehechio on 10/23/21
 */
fun View.gone(): View {
    if (visibility != View.GONE){
        visibility = View.GONE
    }
    return this
}
fun View.invisible(): View {
    if (visibility != View.INVISIBLE){
        visibility = View.INVISIBLE
    }
    return this
}

fun View.visible(): View {
    if (visibility != View.VISIBLE){
        visibility = View.VISIBLE
    }
    return this
}