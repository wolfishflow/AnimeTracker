package com.animetracker.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.animetracker.databinding.AtpItemBinding

// todo rename this class
class AnimeSortedByAdapter constructor(
    private val onClick:(GetAnimeSortedByPopularityQuery.Medium, ImageView) -> Unit
) : PagingDataAdapter<GetAnimeSortedByPopularityQuery.Medium, AnimeSortedByViewHolder>(AnimeSortedByComparator) {
    override fun onBindViewHolder(holder: AnimeSortedByViewHolder, position: Int) {
        holder.setData(getItem(position)!!, onClick)
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
    fun setData(
        data: GetAnimeSortedByPopularityQuery.Medium,
        onClick: (GetAnimeSortedByPopularityQuery.Medium, ImageView) -> Unit) {

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
        binding.coverImageView.transitionName = data.id.toString()
        binding.coverImageView.load(data.coverImage?.extraLarge) {
            crossfade(300)
            // Needed for shared element transition
            allowHardware(false)
            data.coverImage?.color?.let { placeholder(ColorDrawable(Color.parseColor(it))) }
        }

        binding.root.setOnClickListener{
            onClick(data, binding.coverImageView)
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
