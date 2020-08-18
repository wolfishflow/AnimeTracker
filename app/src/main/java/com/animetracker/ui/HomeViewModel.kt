package com.animetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class HomeViewModel : ViewModel() {

    val result: Flow<PagingData<GetAllTimePopularAnimeQuery.Medium>> =
        Pager(PagingConfig(20)) {
            AllTimePopularPagingSource()
        }.flow.cachedIn(viewModelScope)

}