package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stevehechio.apps.mziiketrailers.R
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesCategory
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.FragmentHomeMoviesBinding
import com.stevehechio.apps.mziiketrailers.ui.activities.SearchMoviesActivity
import com.stevehechio.apps.mziiketrailers.ui.adapters.CategoryAdapter
import com.stevehechio.apps.mziiketrailers.ui.viewmodels.MoviesViewModel
import com.stevehechio.apps.mziiketrailers.utils.gone
import com.stevehechio.apps.mziiketrailers.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import kotlin.reflect.KFunction4

@AndroidEntryPoint
class HomeMoviesFragment : Fragment() {
    private var _binding: FragmentHomeMoviesBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentChangeListener:
            KFunction4<@ParameterName(name = "fragID") String, String?, MoviesCategory?, String, Unit>
    private lateinit var viewModel: MoviesViewModel
    private lateinit var mAdapter: CategoryAdapter
    private val arrayList = ArrayList<MoviesCategory>()

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
        binding.ivSearch.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(),SearchMoviesActivity::class.java)) }
        mAdapter = CategoryAdapter(requireContext())
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {

                    val comingSoonList = ArrayList<MoviesEntity>()
                    val top250Movies = ArrayList<MoviesEntity>()
                    val top250Tvs = ArrayList<MoviesEntity>()
                    val popularMovies = ArrayList<MoviesEntity>()
                    val popularTvs = ArrayList<MoviesEntity>()
                    val inTheaters = ArrayList<MoviesEntity>()

                    response.data?.forEach {
                        when (it.category) {
                            "Top 250 Movies" -> {
                                top250Movies.add(it)
                            }
                            "Top 250 Tv Shows" -> {
                                top250Tvs.add(it)
                            }
                            "Most Popular Movies" -> {
                                popularMovies.add(it)
                            }
                            "Most Popular Tv Shows" -> {
                                popularTvs.add(it)
                            }
                            "Coming Soon" -> {
                                comingSoonList.add(it)
                            }
                            "In Theaters" -> {
                                inTheaters.add(it)
                            }
                        }
                    }
                    arrayList.add(MoviesCategory("Coming Soon",comingSoonList))
                    arrayList.add(MoviesCategory("Top 250 Movies",top250Movies))
                    arrayList.add(MoviesCategory("Top 250 Tv Shows",top250Tvs))
                    arrayList.add(MoviesCategory("Most Popular Movies",popularMovies))
                    arrayList.add(MoviesCategory("Most Popular Tv Shows",popularTvs))
                    arrayList.add(MoviesCategory("In Theaters",inTheaters))
                    stopLoadingWithSucess()
                    mAdapter.submitList(arrayList)
                }
                is Resource.Loading -> {
                    if (mAdapter.itemCount < 1){
                        startLoading()
                    }
                }
                is Resource.Failure -> {
                    val error = response.cause
                    binding.tvError.text = error
                    stopLoadingWithError()
                }
                else -> {
                    val error = response
                    binding.tvError.text = getString(R.string.something_went_wrong)
                    stopLoadingWithError()
                }
            }
        })
        viewModel.fetchMovies()
        mAdapter.setOnSeeAllItems(object : CategoryAdapter.OnSeeAllClick{
            override fun onSeeAllMoviesUnderCategory(moviesCategory: MoviesCategory) {
               onShowAllInCategoryFragment(moviesCategory)
            }

        })
        mAdapter.setOnViewDetailsClicked(object : CategoryAdapter.OnViewDetailsClicked{
            override fun onViewItemDetails(id: String) {
               onDetailsFragment(id)
            }

        })
    }

    private fun stopLoadingWithSucess() {
        binding.rv.visible()
        binding.pb.gone()
        binding.tvError.gone()
    }
    private fun stopLoadingWithError() {
        binding.rv.gone()
        binding.pb.gone()
        binding.tvError.visible()
    }

    private fun startLoading() {
        binding.rv.gone()
        binding.pb.visible()
        binding.tvError.gone()
    }

    private fun onShowAllInCategoryFragment(moviesCategory: MoviesCategory){
        fragmentChangeListener.invoke(ShowAllFragment.FRAG_ID,null,moviesCategory, FRAG_ID)
    }

    private fun onDetailsFragment(id: String){
        fragmentChangeListener.invoke(DetailsFragment.FRAG_ID,id,null, FRAG_ID)
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
            KFunction4<String, String?, MoviesCategory?, String, Unit>
        ) =
            HomeMoviesFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }


}