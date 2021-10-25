package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesCategory
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentShowAllBinding
import com.stevehechio.apps.mziiketrailers.ui.adapters.CategoryAdapter
import com.stevehechio.apps.mziiketrailers.ui.adapters.HomeMoviesAdapter
import com.stevehechio.apps.mziiketrailers.utils.gone
import com.stevehechio.apps.mziiketrailers.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KFunction4
import kotlin.reflect.KFunction5

@AndroidEntryPoint
class ShowAllFragment : Fragment() {
    private var _binding: FragmentShowAllBinding? = null
    private val binding get() =  _binding!!
    private lateinit var fragmentChangeListener:
            KFunction4<@ParameterName(name = "fragID") String, String?, MoviesCategory?, String, Unit>

    private var moviesCategory: MoviesCategory? = null
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
    }

    private fun setUpViews() {
        binding.tvBack.setOnClickListener { onShowHomeMoviesFragment() }
        moviesCategory = arguments?.get(MOVIES_ENTITIES) as MoviesCategory? ?: return
        binding.tvCatTitle.text = moviesCategory!!.name
        val mAdapter = HomeMoviesAdapter(requireContext())
        binding.rv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = mAdapter
        }
        mAdapter.submitList(moviesCategory!!.movieEntityList)
        if (mAdapter.itemCount > 0){
            binding.pb.gone()
            binding.tvError.gone()
        }else {
            binding.pb.gone()
            binding.tvError.visible()
            binding.tvError.text = getString(R.string.something_went_wrong)
        }
        mAdapter.setOnClickedItem(object : HomeMoviesAdapter.OnClickItemListener{
            override fun onItemClicked(id: String) {
               onDetailsFragment(id)
            }

        })
    }

    private fun onShowHomeMoviesFragment(){
        fragmentChangeListener.invoke(HomeMoviesFragment.FRAG_ID,null,null,FRAG_ID)
    }

    private fun onDetailsFragment(id: String){
        fragmentChangeListener.invoke(DetailsFragment.FRAG_ID,id,null, HomeMoviesFragment.FRAG_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.ShowAllFragment"
        const val MOVIES_ENTITIES = "MOVIES_ENTITIES.ShowAllFragment"
        @JvmStatic
        fun newInstance(
            fragmentChangeListener:
            KFunction4<String, String?, MoviesCategory?, String, Unit>
        ) =
            ShowAllFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }
}