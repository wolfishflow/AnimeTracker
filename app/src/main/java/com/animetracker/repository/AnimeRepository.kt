package com.animetracker.repository

import GetAllDetailsOfAnimeQuery
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.animetracker.ui.AnimeSortedByPagingSource
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi

// TODO I should be able to use @Inject here, but I seem to have some dependency issue
class AnimeRepository constructor(private val apolloClient: ApolloClient) {

    fun getTrendingAnime() = Pager(PagingConfig(20)) {
        AnimeSortedByPagingSource(apolloClient)
    }.flow

    @ExperimentalCoroutinesApi
    suspend fun getAnimeDetails (animeId: Int): Response<GetAllDetailsOfAnimeQuery.Data> {
        return apolloClient.query(GetAllDetailsOfAnimeQuery(animeId)).toDeferred().await()
    }
}
