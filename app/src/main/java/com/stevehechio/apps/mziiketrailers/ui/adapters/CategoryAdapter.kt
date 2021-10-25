package com.stevehechio.apps.mziiketrailers.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesCategory
import com.stevehechio.apps.mziiketrailers.data.local.entities.MoviesEntity
import com.stevehechio.apps.mziiketrailers.databinding.ItemCategoryListBinding
import com.stevehechio.apps.mziiketrailers.utils.gone
import com.stevehechio.apps.mziiketrailers.utils.visible
import java.util.ArrayList

/**
 * Created by stevehechio on 10/25/21
 */
class CategoryAdapter(val context: Context):
    ListAdapter<MoviesCategory, CategoryAdapter.CategoryHolder>(MoviesCatDiff) {
    var onSeeAll: OnSeeAllClick? = null
    var onItemClickListener: OnViewDetailsClicked? = null

    fun setOnSeeAllItems(onSeeAll: OnSeeAllClick){
        this.onSeeAll = onSeeAll
    }

    fun setOnViewDetailsClicked(onItemClickListener: OnViewDetailsClicked){
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(ItemCategoryListBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bindViews(getItem(position))
    }
    inner class CategoryHolder(private val binding: ItemCategoryListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViews(moviesCategory: MoviesCategory){
            binding.txtCat.text = moviesCategory.name
            if (moviesCategory.name == "Coming Soon") binding.tvAll.gone() else binding.tvAll.visible()
            val mAdapter = HomeMoviesAdapter(context)
            binding.rvCat.apply {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter = mAdapter
            }
            mAdapter.submitList(moviesCategory.movieEntityList)
            binding.tvAll.setOnClickListener {
                onSeeAll?.onSeeAllMoviesUnderCategory(moviesCategory) }
            mAdapter.setOnClickedItem(object : HomeMoviesAdapter.OnClickItemListener{
                override fun onItemClicked(id: String) {
                    onItemClickListener?.onViewItemDetails(id)
                }

            })
        }

    }

    object MoviesCatDiff: DiffUtil.ItemCallback<MoviesCategory>(){
        override fun areItemsTheSame(
            oldItem: MoviesCategory,
            newItem: MoviesCategory
        ): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MoviesCategory,
            newItem: MoviesCategory
        ): Boolean {
            return oldItem.movieEntityList[0].id == newItem.movieEntityList[0].id
        }

    }
interface OnSeeAllClick{
    fun onSeeAllMoviesUnderCategory(moviesCategory: MoviesCategory)
}
   interface OnViewDetailsClicked{
    fun onViewItemDetails(id: String)
   }
}