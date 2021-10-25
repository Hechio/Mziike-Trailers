package com.stevehechio.apps.mziiketrailers.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.ItemComingSoonListBinding
import com.stevehechio.apps.mziiketrailers.databinding.ItemMoviesListBinding
import com.stevehechio.apps.mziiketrailers.databinding.ItemTop250MoviesListBinding
import com.stevehechio.apps.mziiketrailers.ui.adapters.viewholders.MoviesViewHolder
import com.stevehechio.apps.mziiketrailers.utils.gone
import java.util.*

/**
 * Created by stevehechio on 10/25/21
 */
class HomeMoviesAdapter(val context: Context):
    ListAdapter<MoviesEntity, RecyclerView.ViewHolder>(HomeMoviesDiff) {
    private var lastPosition = -1
    var onClickLikedListener :OnClickItemListener? = null

    fun setOnClickedItem(onClickLikedListener :OnClickItemListener){
        this.onClickLikedListener = onClickLikedListener
    }
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).category ?: "") {
            "Coming Soon" -> {
                TYPE_COMING_SOON
            }
            "Top 250 Movies" -> {
                TYPE_TOP_250
            }
            else -> {
                TYPE_OTHER
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COMING_SOON -> {
                ComingSoonHolder(ItemComingSoonListBinding
                    .inflate(LayoutInflater.from(parent.context),parent,false))
            }
            TYPE_TOP_250 -> {
                MoviesViewHolder(ItemTop250MoviesListBinding
                    .inflate(LayoutInflater.from(parent.context),parent,false),context)
            }
            else -> {
                OtherMovieCatsHolder(ItemMoviesListBinding
                    .inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setAnimation(holder.itemView,position)
        when {
            getItemViewType(position) == TYPE_COMING_SOON -> {
                (holder as ComingSoonHolder).bindViews(getItem(position))
            }
            getItemViewType(position) == TYPE_TOP_250 -> {
                (holder as MoviesViewHolder).bindViews(getItem(position),onClickLikedListener)

            }
            else -> {
                (holder as OtherMovieCatsHolder).bindViews(getItem(position))
            }
        }
    }
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            anim.duration =
                Random().nextInt(501).toLong() //to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }
    inner class ComingSoonHolder(val binding: ItemComingSoonListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(moviesEntity: MoviesEntity){
            val mUrl = moviesEntity.image
            Glide.with(context)
                .load(mUrl)
                .centerCrop()
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
                .into(binding.ivComingSoonMovie)
            binding.ivComingSoonMovie.setOnClickListener { onClickLikedListener?.onItemClicked(moviesEntity.id) }
        }
    }
    inner class OtherMovieCatsHolder(val binding: ItemMoviesListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(moviesEntity: MoviesEntity){
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
            binding.tvYear.text = moviesEntity.year
            val rating = moviesEntity.imDbRating ?: ""
            if (rating.isNotEmpty()){
                val firstChar = rating[0].toString()
                val lstChar = rating.substring(1)
                binding.tvRatingWhole.text = firstChar
                binding.tvRatingDecimal.text = lstChar
            }else {
                binding.tvRatingWhole.text = "0"
                binding.tvRatingDecimal.text =".0"
            }
            binding.iv250Movies.setOnClickListener { onClickLikedListener?.onItemClicked(moviesEntity.id) }

        }
    }

    object HomeMoviesDiff: DiffUtil.ItemCallback<MoviesEntity>(){
        override fun areItemsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoviesEntity, newItem: MoviesEntity): Boolean {
            return  oldItem == newItem
        }

    }

    companion object {
        private const val TYPE_COMING_SOON = 1
        private const val TYPE_TOP_250 = 2
        private const val TYPE_OTHER = 3
    }
interface OnClickItemListener{
    fun onItemClicked(id: String)
}
}