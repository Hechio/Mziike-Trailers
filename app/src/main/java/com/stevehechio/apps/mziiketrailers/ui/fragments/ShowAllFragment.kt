package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentShowAllBinding
import kotlin.reflect.KFunction4
import kotlin.reflect.KFunction5


class ShowAllFragment : Fragment() {
    private var _binding: FragmentShowAllBinding? = null
    private val binding get() =  _binding!!
    private lateinit var fragmentChangeListener:
            KFunction5<@ParameterName(name = "fragID") String, MoviesEntity?,String?,
                    List<MoviesEntity>?, String, Unit>

    private var moviesEntityList: List<MoviesEntity>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowAllBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        moviesEntityList = arguments?.get(MOVIES_ENTITIES) as List<MoviesEntity>? ?: return
    }

    private fun setUpViews() {
        //this is just for testing navigation
        binding.tvTest.setOnClickListener { onDetailsFragment() }
        binding.tvTest2.setOnClickListener { onShowHomeMoviesFragment()}
    }

    private fun onShowHomeMoviesFragment(){
        fragmentChangeListener.invoke(HomeMoviesFragment.FRAG_ID,null,null,null,FRAG_ID)
    }

    private fun onDetailsFragment(){
        //todo add selected movie entity and category to the invoke method
        fragmentChangeListener.invoke(DetailsFragment.FRAG_ID,null,null,null,FRAG_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.ShowAllFragment"
        const val MOVIES_ENTITIES = "MOVIES_ENTITIES.ShowAllFragment"
        const val CATEGORY = "CATEGORY.ShowAllFragment"
        @JvmStatic
        fun newInstance(
            fragmentChangeListener:
            KFunction5<String, MoviesEntity?, String?, List<MoviesEntity>?, String, Unit>
        ) =
            ShowAllFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }
}