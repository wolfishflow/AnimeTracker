package com.animetracker.ui

import FindTitleQuery
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.animetracker.R
import com.animetracker.network.AniListClient
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            getTitle()
        }
    }

    private suspend fun getTitle() {
        var result: FindTitleQuery.Media?

        withContext(Dispatchers.IO) {
            val apolloClient = AniListClient().getClient()
            val response = apolloClient.query(FindTitleQuery(1)).toDeferred().await()
            result = response.data?.media
        }

        result?.let {
            Toast.makeText(context, result!!.title?.english, Toast.LENGTH_SHORT).show()
        }
    }
}