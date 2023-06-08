package com.example.tmdb_android_clone.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.adapter.MovListAdapters
import com.example.tmdb_android_clone.databinding.FragmentFavouriteListBinding
import com.example.tmdb_android_clone.models.nowplaying.Result
import com.example.tmdb_android_clone.viewmodels.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavouriteListFragment : Fragment() {

  private lateinit var binding: FragmentFavouriteListBinding
  private lateinit var watchList: ArrayList<String>
  private lateinit var watchlistItem: ArrayList<Result>
  private val mainViewModel: MainViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    binding = FragmentFavouriteListBinding.inflate(layoutInflater)

    // intializing the arraylist to filter out the title stored in the shared prefs
    watchlistItem = ArrayList()
    watchList = ArrayList()

    //read the data from the shared prefs
    readData()

    val movies = mainViewModel.movies.value

    val listOfMovies = movies?.results

    // searching title match from the database list
    for (watchData in watchList) {
      for (item in listOfMovies!!) {
        if (watchData == item.title) {
          watchlistItem.add(item)
        }
      }
    }


    // setting up the recyclerview
    binding.favMovieListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.favMovieListRecyclerView.adapter =
      MovListAdapters(requireContext(), watchlistItem,"favourite")

    return binding.root
  }

  // simple process to reading the data from the sharedprefrences.
  private fun readData() {
    val sharedPreferences =
      requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
    val type = object : TypeToken<ArrayList<String>>() {}.type
    watchList = gson.fromJson(json, type)
  }

}