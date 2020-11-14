package com.animetracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.animetracker.databinding.FragmentHomeBinding
import com.animetracker.ui.AnimeSortedByAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var adapter: AnimeSortedByAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        adapter = AnimeSortedByAdapter(this::navigateToDetails)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            homeViewModel.trendingAnime.collectLatest { pagingData ->
                pagingData.let {
                    // todo handle empty data?
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun navigateToDetails(data: GetAnimeSortedByPopularityQuery.Medium) {
        val detailsDirection = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(data.id)
        findNavController().navigate(detailsDirection)
    }
}