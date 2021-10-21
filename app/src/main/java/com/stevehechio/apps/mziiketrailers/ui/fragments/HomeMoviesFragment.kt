package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHomeMoviesBinding
import kotlin.reflect.KFunction4
import kotlin.reflect.KFunction5

class HomeMoviesFragment : Fragment() {
    private var _binding: FragmentHomeMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentChangeListener:
            KFunction5<@ParameterName(name = "fragID") String, MoviesEntity?,String?,
                    List<MoviesEntity>?, String, Unit>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMoviesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {
        //These is used to test the navigation between fragments
        binding.tvTest.setOnClickListener { onShowAllInCategoryFragment() }
        binding.tvTest2.setOnClickListener { onDetailsFragment() }
    }


    private fun onShowAllInCategoryFragment(){
        //todo add list of movies under the selected category
        fragmentChangeListener.invoke(ShowAllFragment.FRAG_ID,null,null, null,FRAG_ID)
    }

    private fun onDetailsFragment(){
        //todo add selected movie entity to the invoke method
        fragmentChangeListener.invoke(DetailsFragment.FRAG_ID,null,null, null,FRAG_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.HomeMoviesFragment"
        @JvmStatic
        fun newInstance(
            fragmentChangeListener:
            KFunction5<String, MoviesEntity?, String?, List<MoviesEntity>?, String, Unit>
        ) =
            HomeMoviesFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }
}