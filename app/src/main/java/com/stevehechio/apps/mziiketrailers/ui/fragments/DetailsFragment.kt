package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentDetailsBinding
import kotlin.reflect.KFunction4
import kotlin.reflect.KFunction5

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var moviesEntity: MoviesEntity? = null
    private lateinit var fragmentChangeListener:
            KFunction5<@ParameterName(name = "fragID") String, MoviesEntity?,String?,
                    List<MoviesEntity>?, String, Unit>
    private var originFrag: String?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setUpViews()
    }

    private fun setUpViews() {
        moviesEntity = arguments?.get(MOVIE_ENTITY) as MoviesEntity? ?: return
        originFrag = arguments?.get(ORIGIN_FRAG) as String? ?: ""
    }


    private fun onChangeFragment(){
        if (originFrag == ShowAllFragment.FRAG_ID) onShowAllFragment() else onHomeMoviesFragment()
    }
    private fun onHomeMoviesFragment(){
        fragmentChangeListener.invoke(HomeMoviesFragment.FRAG_ID,null,null,null,FRAG_ID)
    }

    private fun onShowAllFragment(){
        //todo add selected movie entity and category to the invoke method
        fragmentChangeListener.invoke(DetailsFragment.FRAG_ID,null,null,null,FRAG_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.DetailsFragment"
        const val MOVIE_ENTITY = "MOVIE_ENTITY.DetailsFragment"
        const val ORIGIN_FRAG = "ORIGIN_FRAG.DetailsFragment"

        @JvmStatic
        fun newInstance(
            fragmentChangeListener:
            KFunction5<String, MoviesEntity?, String?, List<MoviesEntity>?, String, Unit>
        ) =
            DetailsFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }
}