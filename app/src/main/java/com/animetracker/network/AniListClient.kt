package com.animetracker.network

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class AniListClient {

    private var apolloClient: ApolloClient? = null

    fun getClient(): ApolloClient {
        apolloClient?.let { return@let }
        apolloClient = createClient()
        return apolloClient as ApolloClient
    }

    private fun createClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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