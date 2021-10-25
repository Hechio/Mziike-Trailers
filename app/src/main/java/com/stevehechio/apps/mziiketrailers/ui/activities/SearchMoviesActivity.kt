package com.stevehechio.apps.mziiketrailers.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.remote.model.MoviesSearch
import com.stevehechio.apps.mziiketrailers.databinding.ActivitySearchMoviesBinding
import com.stevehechio.apps.mziiketrailers.ui.adapters.SearchMovieAdapter
import com.stevehechio.apps.mziiketrailers.ui.viewmodels.SearchMoviesViewModel
import com.stevehechio.apps.mziiketrailers.utils.gone
import com.stevehechio.apps.mziiketrailers.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchMoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchMoviesBinding

    private lateinit var viewModel: SearchMoviesViewModel
    private lateinit var mAdapter: SearchMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        mAdapter = SearchMovieAdapter(this)
        viewModel = ViewModelProvider(this).get(SearchMoviesViewModel::class.java)
        viewModel.movieList.observe(this,{response ->
            when(response){
                is Resource.Success -> {
                    val data = response.data
                    binding.rv.apply {
                        layoutManager = LinearLayoutManager(applicationContext)
                        adapter = mAdapter
                    }
                    mAdapter.submitList(data)
                    binding.pb.gone()
                    binding.rv.visible()
                }
                is Resource.Failure -> {
                    val message = response.cause
                    binding.pb.gone()
                    Toast.makeText(this,"Error: $message", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.pb.visible()
                    binding.rv.gone()
                }
            }
        })

        mAdapter.setOnAddFavClicked(object : SearchMovieAdapter.OnAddToFavCliked{
            override fun onAddToFavClicked(movie: MoviesSearch) {
                Toast.makeText(applicationContext, "${movie.title} added to favorite",Toast.LENGTH_SHORT).show()
            }

        })
        setUpSearchView()
    }

    private fun setUpSearchView() {
        binding.svMain.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val query = p0?.trim() ?: ""
                if (query.isNotEmpty()){
                    viewModel.getMoviesLists(query)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }
}