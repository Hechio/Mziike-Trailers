package com.stevehechio.apps.mziiketrailers.ui.adapters.viewholders

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.ItemTop250MoviesListBinding
import com.stevehechio.apps.mziiketrailers.ui.adapters.HomeMoviesAdapter
import com.stevehechio.apps.mziiketrailers.utils.gone

/**
 * Created by stevehechio on 10/25/21
 */
class MoviesViewHolder(val binding: ItemTop250MoviesListBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
    fun bindViews(
        moviesEntity: MoviesEntity,
        onClickLikedListener: HomeMoviesAdapter.OnClickItemListener?
    ) {
        val mUrl = moviesEntity.image
        Glide.with(context)
            .load(mUrl)
            .centerCrop()
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {

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

        binding.tvTitle.text = moviesEntity.title
            binding.iv250Movies.setOnClickListener { onClickLikedListener?.onItemClicked(moviesEntity.id) }
    }
}