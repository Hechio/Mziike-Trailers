package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHomeMoviesBinding

class HomeMoviesFragment : Fragment() {
    private var _binding: FragmentHomeMoviesBinding? = null
    private val binding get() = _binding!!
    private var onFragmentChangeListener: HolderFragment.OnFragmentChangeListener? = null

    private fun setOnFragmentChangeListener(onFragmentChangeListener: HolderFragment.OnFragmentChangeListener){
        this.onFragmentChangeListener = onFragmentChangeListener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.HomeMoviesFragment"
        @JvmStatic
        fun newInstance(onFragmentChangeListener: HolderFragment.OnFragmentChangeListener) :
            HomeMoviesFragment {
                val frag = HomeMoviesFragment()
                frag.setOnFragmentChangeListener(onFragmentChangeListener)
                return frag
            }
    }
}