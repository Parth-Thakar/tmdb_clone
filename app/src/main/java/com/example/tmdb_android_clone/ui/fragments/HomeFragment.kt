package com.example.tmdb_android_clone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.adapter.MovListAdapters
import com.example.tmdb_android_clone.databinding.FragmentHomeBinding
import com.example.tmdb_android_clone.viewmodels.MainViewModel

class HomeFragment : Fragment() {
  private val mainViewModel: MainViewModel by activityViewModels()

  private lateinit var binding: FragmentHomeBinding
  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var adapters: MovListAdapters

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentHomeBinding.inflate(layoutInflater)

    // setting up the recyclerview
    binding.movListRecyclerView.setHasFixedSize(true)
    layoutManager = LinearLayoutManager(requireContext())
    binding.movListRecyclerView.layoutManager = layoutManager


    //Setting up the Adapter to show all the movies fetched form the NowPlaying API
    mainViewModel.movies.observe(viewLifecycleOwner){
      adapters = MovListAdapters(requireContext(), it.results,"home")
      binding.movListRecyclerView.adapter = adapters
      binding.spinKitViewHome.visibility = View.GONE
    }


    binding.sortButton.setOnClickListener{
      val moviesRes = mainViewModel.movies.value
      val movies = moviesRes?.results
      // kotlin collection function to sort the list according to the release date.
      val sortedList = movies?.sortedByDescending { it.release_date }
      adapters = MovListAdapters(requireContext(), sortedList!!,"home")
      binding.movListRecyclerView.adapter = adapters

      Toast.makeText(requireContext(),"List is Sorted From Newest",Toast.LENGTH_SHORT).show()
    }

    return binding.root
  }


}