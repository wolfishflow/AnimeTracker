package com.animetracker.di

import com.apollographql.apollo.ApolloClient
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApolloModule {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        return createClient()
    }

    private fun createClient(): ApolloClient {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor.Builder().setLevel(Level.BODY).build())
            .build()

        return ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(okHttpClient)
            .build()
    }

    companion object {
        private const val serverUrl: String = "https://graphql.anilist.co"
    }
}
