package com.animetracker.ui

import GetAnimeSortedByPopularityQuery
import androidx.paging.PagingSource
import com.animetracker.network.AniListClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import type.MediaSort

// todo rename this class
class AnimeSortedByPagingSource(private val sortingFilter: Input<List<MediaSort>>) : PagingSource<Int, GetAnimeSortedByPopularityQuery.Medium>() {
    override suspend fun load(params: LoadParams<Int>):
        LoadResult<Int, GetAnimeSortedByPopularityQuery.Medium> {

            // default to 1 if new
            val currentKey = params.key ?: 1
            val response =
                AniListClient().getClient()
                    .query(GetAnimeSortedByPopularityQuery(pageNumber = currentKey, sortingFilter = sortingFilter))
                    .toDeferred()
                    .await()

            response.data?.page?.let { page ->
                val hasNext = page.pageInfo?.hasNextPage
                page.media?.let {
                    // it == List<GetAlllTimePopularAnimeQuery.Medium?> but LoadResult.Page()
                    // needs data to be non-nullable.
                    // Side Effect: The list can be empty - handle this on the front end

                    return LoadResult.Page(
                        data = it.filterNotNull().toList(),
                        prevKey = if (currentKey == 1) null else currentKey - 1,
                        nextKey = if (hasNext!!) currentKey + 1 else null
                    )
                }
            }

            return LoadResult.Error(Exception())
        }
}
