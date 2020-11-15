package com.animetracker.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animetracker.repository.AnimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    private val _detailsLiveData: MutableLiveData<GetAllDetailsOfAnimeQuery.Data> = MutableLiveData()
    val detailsLiveData: LiveData<GetAllDetailsOfAnimeQuery.Data> get() = _detailsLiveData

    @ExperimentalCoroutinesApi
    fun fetchDetails(animeId: Int) {
        viewModelScope.launch {
            _detailsLiveData.value = animeRepository.getAnimeDetails(animeId).data
        }
    }
}
