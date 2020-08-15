package com.animetracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.animetracker.databinding.FragmentHomeBinding

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

//        viewLifecycleOwner.lifecycleScope.launch {
//            getTitle()?.let { loadArtAndTitle(it) }
//        }
    }

//    private suspend fun getTitle(): GetNeonGenesisEvangelionAnimeQuery.Media? {
//        val neonGenesisResult: GetNeonGenesisEvangelionAnimeQuery.Media?
//        withContext(Dispatchers.IO) {
//            val apolloClient = AniListClient().getClient()
//            val response = apolloClient.query(GetNeonGenesisEvangelionAnimeQuery()).toDeferred().await()
//            neonGenesisResult = response.data?.media
//        }
//        return neonGenesisResult
//    }
//
//    private fun loadArtAndTitle(result :GetNeonGenesisEvangelionAnimeQuery.Media) {
//        binding.titleTextView.text = result.title?.english
//        binding.coverImageImageView.load(result.coverImage?.extraLarge) {
//            crossfade(true)
//            placeholder(ColorDrawable(Color.parseColor(result.coverImage?.color)))
//        }
//    }
}