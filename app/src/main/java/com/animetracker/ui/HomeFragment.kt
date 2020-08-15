package com.animetracker.ui

import FindTitleQuery
import GetNeonGenesisEvangelionAnimeQuery
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.api.load
import coil.transform.CircleCropTransformation
import com.animetracker.R
import com.animetracker.databinding.FragmentHomeBinding
import com.animetracker.network.AniListClient
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment() {

    private var _binding :FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            getTitle()?.let { loadArtAndTitle(it) }
        }
    }

    private suspend fun getTitle(): GetNeonGenesisEvangelionAnimeQuery.Media? {
        val neonGenesisResult: GetNeonGenesisEvangelionAnimeQuery.Media?
        withContext(Dispatchers.IO) {
            val apolloClient = AniListClient().getClient()
            val response = apolloClient.query(GetNeonGenesisEvangelionAnimeQuery()).toDeferred().await()
            neonGenesisResult = response.data?.media
        }
        return neonGenesisResult
    }

    private fun loadArtAndTitle(result :GetNeonGenesisEvangelionAnimeQuery.Media) {
        binding.titleTextView.text = result.title?.english
        binding.coverImageImageView.load(result.coverImage?.extraLarge) {
            crossfade(true)
            placeholder(ColorDrawable(Color.parseColor(result.coverImage?.color)))
        }
    }
}