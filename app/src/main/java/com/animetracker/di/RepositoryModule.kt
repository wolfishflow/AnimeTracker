package com.animetracker.di

import com.animetracker.repository.AnimeRepository
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    fun provideAnimeRepository(apolloClient: ApolloClient) : AnimeRepository {
        return AnimeRepository(apolloClient)
    }
}