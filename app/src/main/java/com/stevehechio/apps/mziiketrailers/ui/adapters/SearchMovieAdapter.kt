package com.stevehechio.apps.mziiketrailers.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.stevehechio.apps.mziiketrailers.data.remote.model.MoviesSearch
import com.stevehechio.apps.mziiketrailers.databinding.ItemSearchListBinding
import com.stevehechio.apps.mziiketrailers.utils.gone

/**
 * Created by stevehechio on 10/23/21
 */
class SearchMovieAdapter(val context: Context): ListAdapter<MoviesSearch, SearchMovieAdapter.MoviesSearchViewHolder>(
    MoviesDiff
) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesSearchViewHolder {
        return MoviesSearchViewHolder(ItemSearchListBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MoviesSearchViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }


    inner class MoviesSearchViewHolder(val binding: ItemSearchListBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindViews(movie: MoviesSearch){
            val mUrl = movie.image
            Glide.with(context)
                .load(mUrl)
                .centerCrop()
                .dontAnimate()
                .listener(object : RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.pb.gone()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.pb.gone()
                        return false
                    }
                })
                .into(binding.iv250Movies)
            binding.title.text = movie.title
            binding.tvDesc.text = movie.description
        }
    }

    object MoviesDiff: DiffUtil.ItemCallback<MoviesSearch>(){
        override fun areItemsTheSame(oldItem: MoviesSearch, newItem: MoviesSearch): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesSearch, newItem: MoviesSearch): Boolean {
            return  oldItem == newItem
        }

    }


}