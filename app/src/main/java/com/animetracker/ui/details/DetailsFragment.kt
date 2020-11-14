package com.animetracker.ui.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.animetracker.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailsViewModel.fetchDetails(args.animeId)

        detailsViewModel.detailsLiveData.observe(viewLifecycleOwner) { data ->
            with(data.media!!) {
                binding.coverImageView.load(coverImage?.extraLarge) {
                    crossfade(300)
                    coverImage?.color?.let { placeholder(ColorDrawable(Color.parseColor(it))) }
                }
                binding.titleTextView.text = title?.english ?: title?.romaji
                binding.typeTextView.text = type?.rawValue
                binding.statusTextView.text = status?.rawValue
            }
        }
    }
}