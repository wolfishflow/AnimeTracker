package com.animetracker.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.animetracker.repository.AnimeRepository

class HomeViewModel @ViewModelInject constructor(val animeRepository: AnimeRepository) : ViewModel() {

    val trendingAnime = animeRepository.getTrendingAnime().cachedIn(viewModelScope)
}
