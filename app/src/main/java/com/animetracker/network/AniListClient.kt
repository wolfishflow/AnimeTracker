package com.animetracker.network

import com.apollographql.apollo.ApolloClient
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient

class AniListClient {

    private var apolloClient: ApolloClient? = null

    fun getClient(): ApolloClient {
        apolloClient?.let { return@let }
        apolloClient = createClient()
        return apolloClient as ApolloClient
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