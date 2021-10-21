package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHolderBinding

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

    private fun onFragmentChanged(fragID: String, moviesEntity: MoviesEntity?,idOrCategory: String?,
                                  moviesEntities: List<MoviesEntity>?,originFragID: String){
        when(fragID){
            DetailsFragment.FRAG_ID -> {
                if (moviesEntity != null){
                    onShowDetailsFragment(moviesEntity,idOrCategory)
                }
            }
            ShowAllFragment.FRAG_ID -> {
                if (moviesEntities != null || idOrCategory != null){
                    onShowAllInCategoryFragment(moviesEntities,idOrCategory)
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

    private fun onShowDetailsFragment(moviesEntity: MoviesEntity, idOrCategory: String?){
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putSerializable(DetailsFragment.MOVIE_ENTITY,moviesEntity)
        val detailsFragment = DetailsFragment.newInstance(fragmentChangeListener =  ::onFragmentChanged)
        detailsFragment.arguments = bundle
        transaction.replace(
            R.id.nav_host_fragment,
            detailsFragment,detailsFragment.tag).commit()
        transaction.addToBackStack(detailsFragment.tag)
    }
    private fun onShowAllInCategoryFragment(moviesEntities: List<MoviesEntity>?, idOrCategory: String?){
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(
            R.id.nav_host_fragment,
            ShowAllFragment.newInstance(fragmentChangeListener =  ::onFragmentChanged))
        transaction.commitAllowingStateLoss()
    }

}