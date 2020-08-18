package com.animetracker.ui

import GetAllTimePopularAnimeQuery
import androidx.paging.PagingSource
import com.animetracker.network.AniListClient
import com.apollographql.apollo.coroutines.toDeferred

// todo rename this class
class AllTimePopularPagingSource : PagingSource<Int, GetAllTimePopularAnimeQuery.Medium>() {
    override suspend fun load(params: LoadParams<Int>):
        LoadResult<Int, GetAllTimePopularAnimeQuery.Medium> {

            // default to 1 if new
            val currentKey = params.key ?: 1
            val response =
                AniListClient().getClient()
                    .query(GetAllTimePopularAnimeQuery(currentKey))
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
