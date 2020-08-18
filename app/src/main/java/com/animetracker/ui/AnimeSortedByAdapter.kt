package com.animetracker.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.animetracker.databinding.AtpItemBinding

// todo rename this class
class AnimeSortedByAdapter :
    PagingDataAdapter<GetAnimeSortedByPopularityQuery.Medium, AnimeSortedByViewHolder>(AnimeSortedByComparator) {
    override fun onBindViewHolder(holder: AnimeSortedByViewHolder, position: Int) {
        holder.setData(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeSortedByViewHolder {
        return AnimeSortedByViewHolder(
            AtpItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

// TODO determine how to refactor the ViewHolder + adapter for reusability?
class AnimeSortedByViewHolder(private val binding: AtpItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: GetAnimeSortedByPopularityQuery.Medium) {

        val title = data.title?.let {
            with(it) {
                when {
                    english != null -> {
                        return@let english
                    }
                    romaji != null -> {
                        return@let romaji
                    }
                    else -> {
                        return@let "Unavailable"
                    }
                }
            }
        }

        binding.titleTextView.text = title
        binding.imageView.load(data.coverImage?.extraLarge) {
            crossfade(300)
            data.coverImage?.color?.let { placeholder(ColorDrawable(Color.parseColor(it))) }
        }
    }
}

object AnimeSortedByComparator : DiffUtil.ItemCallback<GetAnimeSortedByPopularityQuery.Medium>() {
    override fun areItemsTheSame(
        oldItem: GetAnimeSortedByPopularityQuery.Medium,
        newItem: GetAnimeSortedByPopularityQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetAnimeSortedByPopularityQuery.Medium,
        newItem: GetAnimeSortedByPopularityQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }
}
