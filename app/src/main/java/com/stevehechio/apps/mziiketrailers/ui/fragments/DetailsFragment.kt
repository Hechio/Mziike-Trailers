package com.stevehechio.apps.mziiketrailers.ui.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.stevehechio.apps.mziiketrailers.data.Resource
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesCategory
import com.stevehechio.apps.mziiketrailers.data.local.entities.Trailer
import com.stevehechio.apps.mziiketrailers.databinding.FragmentDetailsBinding
import com.stevehechio.apps.mziiketrailers.ui.adapters.ActorsAdapter
import com.stevehechio.apps.mziiketrailers.ui.viewmodels.MovieViewModel
import com.stevehechio.apps.mziiketrailers.utils.RatingConvertor
import com.stevehechio.apps.mziiketrailers.utils.gone
import com.stevehechio.apps.mziiketrailers.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KFunction4

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var moviesId: String? = null
    private lateinit var fragmentChangeListener:
            KFunction4<@ParameterName(name = "fragID") String, String?, MoviesCategory?, String, Unit>
    private lateinit var viewModel: MovieViewModel
    private var originFrag: String? = null
    private var moveCategory: MoviesCategory? = null
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
        originFrag = arguments?.get(ORIGIN_FRAG) as String? ?: HomeMoviesFragment.FRAG_ID
        binding.tvBack.setOnClickListener {
            onChangeFragment(originFrag!!)
        }
        moviesId = arguments?.get(MOVIE_ID) as String? ?: return
        moveCategory = arguments?.get(MOVIES_ENTITIES) as MoviesCategory?
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.getMoviesLiveData().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    binding.pb.gone()
                    binding.clDetails.visible()
                    val movieItem = response.data
                    binding.tvTitle.text = movieItem?.title
                    val rating = RatingConvertor.getRating(movieItem?.imDbRating)
                    binding.tvRatings.text = rating
                    binding.ratings.rating = rating.toFloat()
                    binding.tvPlot.text = movieItem?.plot
                    binding.tvGenre.text = movieItem?.genres?.replace(",","  | ") ?: ""
                    val mAdapter = ActorsAdapter(requireContext())
                    binding.rv.apply {
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
                        adapter = mAdapter
                    }
                    mAdapter.submitList(movieItem?.actorList)
                    setUpVideoView(movieItem?.trailer)
                }

                is Resource.Loading -> {
                    binding.pb.visible()
                    binding.clDetails.gone()
                }
                is Resource.Failure -> {
                    binding.pb.visible()
                    binding.clDetails.gone()
                    Toast.makeText(requireContext(),"Error: ${response.cause}",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.pb.visible()
                    binding.clDetails.gone()
                    Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.fetchMovies(moviesId!!)

    }

    private fun setUpVideoView(trailer: Trailer?) {
        val url = Uri.parse(trailer?.linkEmbed)
        binding.videoView.setVideoURI(url)
        binding.videoView.start()

        binding.videoView.setOnErrorListener { mp, what, extra ->
            Toast.makeText(requireContext(),"Cannot Play Trailer",Toast.LENGTH_LONG).show()
            // do something when an error is occur during the video playback
            false
        }
    }


    private fun onChangeFragment(fragId: String) {
        if (fragId == ShowAllFragment.FRAG_ID) onShowAllFragment() else onHomeMoviesFragment()
    }

    private fun onHomeMoviesFragment() {
        requireActivity().getSupportFragmentManager().popBackStack()
        //fragmentChangeListener.invoke(HomeMoviesFragment.FRAG_ID, null, null, FRAG_ID)
    }

    private fun onShowAllFragment() {
        requireActivity().getSupportFragmentManager().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding.videoView.isPlaying){
            binding.videoView.stopPlayback()
        }
        _binding = null
    }

    companion object {
        const val FRAG_ID = "Hechio.DetailsFragment"
        const val MOVIE_ID = "MOVIE_ID.DetailsFragment"
        const val ORIGIN_FRAG = "ORIGIN_FRAG.DetailsFragment"
        const val MOVIES_ENTITIES = "MOVIES_ENTITIES.DetailsFragment"

        @JvmStatic
        fun newInstance(
            fragmentChangeListener:
            KFunction4<String, String?, MoviesCategory?, String, Unit>
        ) =
            DetailsFragment().apply {
                this.fragmentChangeListener = fragmentChangeListener
            }
    }
}