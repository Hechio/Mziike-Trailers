package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesCategory
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHolderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HolderFragment : Fragment() {
    private var _binding: FragmentHolderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHolderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onShowHomeMoviesFragment()
    }

    private fun onFragmentChanged(fragID: String,id: String?,
                                  moviesCategory: MoviesCategory?,originFragID: String){
        when(fragID){
            DetailsFragment.FRAG_ID -> {
                if (id != null){
                    onShowDetailsFragment(id,moviesCategory,originFragID)
                }
            }
            ShowAllFragment.FRAG_ID -> {
                if (moviesCategory != null){
                    onShowAllInCategoryFragment(moviesCategory)
                }
            }
            else -> onShowHomeMoviesFragment()

        }
    }


    private fun onShowHomeMoviesFragment(){
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            HomeMoviesFragment.newInstance(fragmentChangeListener =  ::onFragmentChanged))
        transaction.commitAllowingStateLoss()
    }

    private fun onShowDetailsFragment(id: String?,moviesCategory: MoviesCategory?,originFragID: String?){
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString(DetailsFragment.MOVIE_ID,id)
        bundle.putString(DetailsFragment.ORIGIN_FRAG,originFragID)
        bundle.putSerializable(DetailsFragment.MOVIES_ENTITIES,moviesCategory)
        val detailsFragment = DetailsFragment.newInstance(fragmentChangeListener =  ::onFragmentChanged)
        detailsFragment.arguments = bundle
        transaction.replace(
            R.id.nav_host_fragment,
            detailsFragment,detailsFragment.tag).commit()
        transaction.addToBackStack(detailsFragment.tag)
    }
    private fun onShowAllInCategoryFragment(moviesCategory: MoviesCategory?){
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable(ShowAllFragment.MOVIES_ENTITIES,moviesCategory)
        val showAllFrag = ShowAllFragment.newInstance(fragmentChangeListener = ::onFragmentChanged)
        showAllFrag.arguments = bundle
        transaction.replace(
            R.id.nav_host_fragment,
            showAllFrag,showAllFrag.tag).commit()
        transaction.addToBackStack(showAllFrag.tag)
    }

}