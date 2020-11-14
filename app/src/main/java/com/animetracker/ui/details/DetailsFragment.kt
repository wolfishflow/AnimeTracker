package com.animetracker.ui.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionInflater
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
import java.lang.StringBuilder

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewModel.fetchDetails(args.animeId)

        detailsViewModel.detailsLiveData.observe(viewLifecycleOwner) { data ->
            with(data.media!!) {
                binding.coverImageView.load(coverImage?.extraLarge) {
                    crossfade(300)
                    coverImage?.color?.let { placeholder(ColorDrawable(Color.parseColor(it))) }
                }
                binding.titleTextView.text = title?.english ?: title?.romaji
                binding.typeTextView.text = type?.rawValue ?: "Unavailable" //TODO - perhaps I can validate in VM?
                binding.statusTextView.text = status?.rawValue ?: "Unavailable"

                startDate?.let { date ->
                    //TODO figure out how to handle null + separate int based fields (excluding year)
                    // "startDate": {
                    //    "__typename": "FuzzyDate",
                    //    "year": 2020,
                    //    "month": 10,
                    //    "day": 3
                    // }
                }
                endDate?.let {
                    //TODO figure out how to handle null + separate int based fields
                }

                binding.episodesTextView.text = episodes?.toString() ?: "N/A"
                binding.chaptersTextView.text = chapters?.toString() ?: "N/A"
                binding.volumesTextView.text = volumes?.toString() ?: "N/A"

                genres?.let { list ->
                    val stringBuilder = StringBuilder()
                    list.map {
                        stringBuilder.append(it).append(" ")
                    }
                    binding.genresTextView.text = stringBuilder.toString()
                }

                binding.seasonTextView.text = season?.rawValue ?: "Unavailable"
                binding.seasonYearTextView.text = seasonYear?.toString() ?: "N/A"
            }
        }
    }
}