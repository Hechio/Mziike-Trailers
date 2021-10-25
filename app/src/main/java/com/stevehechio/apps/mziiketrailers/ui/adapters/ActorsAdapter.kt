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
import com.stevehechio.apps.mziiketrailers.data.local.entities.Actor
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.ItemCastListBinding
import com.stevehechio.apps.mziiketrailers.utils.gone
import java.util.*

/**
 * Created by stevehechio on 10/25/21
 */
class ActorsAdapter(val context: Context) : ListAdapter<Actor, ActorsAdapter.ActorsHolder>(ActorDiff){
    private var lastPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsHolder {
        return ActorsHolder(ItemCastListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ActorsHolder, position: Int) {
        holder.bindViews(getItem(position))
        setAnimation(holder.itemView,position)
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

    inner class ActorsHolder(val binding: ItemCastListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(actor: Actor){
            val mUrl = actor.image
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
                .into(binding.ivCast)
            binding.tvCastName.text = actor.name
        }
    }

    object ActorDiff: DiffUtil.ItemCallback<Actor>(){
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return  oldItem == newItem
        }

    }
}