package com.animetracker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.animetracker.databinding.AtpItemBinding

// todo rename this class
class AllTimePopularAdapter :
    PagingDataAdapter<GetAllTimePopularAnimeQuery.Medium, AtpViewHolder>(AtpComparator) {
    override fun onBindViewHolder(holder: AtpViewHolder, position: Int) {
        holder.setData(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtpViewHolder {
        return AtpViewHolder(
            AtpItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

// TODO determine how to refactor the ViewHolder + adapter for reusability?
class AtpViewHolder(private val binding: AtpItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setData(data: GetAllTimePopularAnimeQuery.Medium) {
        // todo handle null english
        binding.titleTextView.text = data.title?.english
        binding.imageView.load(data.coverImage?.extraLarge) {
            crossfade(300)
        }
    }
}

object AtpComparator : DiffUtil.ItemCallback<GetAllTimePopularAnimeQuery.Medium>() {
    override fun areItemsTheSame(
        oldItem: GetAllTimePopularAnimeQuery.Medium,
        newItem: GetAllTimePopularAnimeQuery.Medium
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetAllTimePopularAnimeQuery.Medium,
        newItem: GetAllTimePopularAnimeQuery.Medium
    ): Boolean {
        return oldItem == newItem
    }
}
