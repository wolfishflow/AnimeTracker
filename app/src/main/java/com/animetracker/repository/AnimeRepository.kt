package com.animetracker.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.animetracker.ui.AnimeSortedByPagingSource
import com.apollographql.apollo.ApolloClient

// TODO I should be able to use @Inject here, but I seem to have some dependency issue
class AnimeRepository constructor(private val apolloClient: ApolloClient) {

    fun getTrendingAnime() = Pager(PagingConfig(20)) {
        AnimeSortedByPagingSource(apolloClient)
    }.flow
}
