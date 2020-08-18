package com.animetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apollographql.apollo.api.toInput
import kotlinx.coroutines.flow.Flow
import type.MediaSort

class HomeViewModel : ViewModel() {

    val trendingAnime: Flow<PagingData<GetAnimeSortedByPopularityQuery.Medium>> =
        Pager(PagingConfig(20)) {
            AnimeSortedByPagingSource(listOf(MediaSort.TRENDING_DESC, MediaSort.POPULARITY_DESC).toInput())
        }.flow.cachedIn(viewModelScope)

    val popularAnime: Flow<PagingData<GetAnimeSortedByPopularityQuery.Medium>> =
        Pager(PagingConfig(20)) {
            AnimeSortedByPagingSource(listOf(MediaSort.POPULARITY_DESC, MediaSort.SCORE_DESC).toInput())
        }.flow.cachedIn(viewModelScope)
}
