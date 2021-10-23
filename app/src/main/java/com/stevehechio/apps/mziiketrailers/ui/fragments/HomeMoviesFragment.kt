package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHomeMoviesBinding
import com.stevehechio.apps.mziiketrailers.ui.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KFunction4
import kotlin.reflect.KFunction5

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {
    private var _binding: FragmentHomeMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentChangeListener:
            KFunction5<@ParameterName(name = "fragID") String, MoviesEntity?,String?,
                    List<MoviesEntity>?, String, Unit>
    private lateinit var viewModel: MoviesViewModel

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
    viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, {
            Log.v("Homefrag", "Success Execution! $it")
        })
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