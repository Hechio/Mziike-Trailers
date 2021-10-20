package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stevehechio.apps.mziiketrailers.R

class DetailsFragment : Fragment() {
    private var onFragmentChangeListener: HolderFragment.OnFragmentChangeListener? = null

    private fun setOnFragmentChangeListener(onFragmentChangeListener: HolderFragment.OnFragmentChangeListener){
        this.onFragmentChangeListener = onFragmentChangeListener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(onFragmentChangeListener: HolderFragment.OnFragmentChangeListener, id: String):
            DetailsFragment{
            val frag = DetailsFragment()
            return frag
            }
    }
}