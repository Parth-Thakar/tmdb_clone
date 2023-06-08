package com.example.tmdb_android_clone.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.adapter.SimilarMovieAdapter
import com.example.tmdb_android_clone.databinding.FragmentDetailsBinding
import com.example.tmdb_android_clone.models.details.MovieDetails
import com.example.tmdb_android_clone.viewmodels.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsFragment : Fragment() {

  private val mainViewModel: MainViewModel by activityViewModels()
  val args: DetailsFragmentArgs by navArgs()
  //lateinit var movie : MovieDetails
  private lateinit var binding : FragmentDetailsBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentDetailsBinding.inflate(layoutInflater)
    // Inflate the layout for this fragment

    val id = args.movieid

    mainViewModel.getMovieDetails(id)
    mainViewModel.getMovieReviews(id)
    mainViewModel.getMovieCredits(id)
    mainViewModel.getSimilarMovies(id)
    //var movie : MovieDetails

    mainViewModel.moviesDetails.observe(viewLifecycleOwner){
      binding.movieSummaryTV.text = it.overview
      binding.titleTV.text = it.title
      Glide.with(requireContext())
        .load("https://image.tmdb.org/t/p/original"+it.poster_path)
        .thumbnail(Glide.with(requireContext()).load(R.drawable.spinner))
        .into(binding.moviePosterImage)
      addToFav(it)
      shareButtonEvent(it)
    }

    mainViewModel.movieReviewsLiveData.observe(viewLifecycleOwner){
      binding.movieReviewIntroTV.text = "Movie Review Given By " + it.results[0].author
      binding.movieReviewTV.text = it.results[0].content
    }
    val castNameList = ArrayList<String>()
    val crewNameList = ArrayList<String>()

    mainViewModel.movieCreditsLiveData.observe(viewLifecycleOwner){
      for(i in 0..10)
      {
        castNameList.add(it.cast[i].name)
      }
      for (i in 0..10)
      {
        crewNameList.add(it.crew[i].name)
      }
      binding.movieCastCreditsTV.text = "Cast Credits : "+ castNameList.toString()
      binding.movieCrewCreditsTV.text = "Crew Credits : " + crewNameList.toString()
    }






    binding.similarMoviesRecyclerView.setHasFixedSize(true)
    val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
    binding.similarMoviesRecyclerView.layoutManager = layoutManager

    mainViewModel.similarMoviesLiveData.observe(viewLifecycleOwner){
      binding.similarMoviesRecyclerView.adapter = SimilarMovieAdapter(it.results,requireContext())
    }



    return binding.root
  }

  private fun shareButtonEvent(data: MovieDetails?) {
    // Functionality of Share Button in detail Fragment, Using Implicit Intent
    binding.shareButton.setOnClickListener {
      val drawable: BitmapDrawable = binding.moviePosterImage.drawable as BitmapDrawable
      val bitmap: Bitmap = drawable.bitmap

      val bitmapPath: String = MediaStore.Images.Media.insertImage(
        activity?.applicationContext?.contentResolver,
        bitmap,
        "movimage",
        null
      )

      val uri: Uri = Uri.parse(bitmapPath)

      val intent = Intent(Intent.ACTION_SEND)
      intent.setType("image/png")
      intent.putExtra(Intent.EXTRA_STREAM, uri)
      intent.putExtra(
        Intent.EXTRA_TEXT,
        "Product Name : ${data?.title} \n Release : ${data?.release_date} \n Language : ${data?.original_language}"
      )
      startActivity(Intent.createChooser(intent, "Share the movie"))


    }

  }

  // addToFav() method
  private fun addToFav(data: MovieDetails) {
    readData()
    // Cheking if the user has clicked the heart Icon in the detail fragment then chaging it to red and if did twice
    // then it to return back to normal
    isCheck = if (watchList!!.contains(data.title)) {
      binding.likeButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
      true
    } else {
      binding.likeButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
      false
    }
    // when heart icon is clicked
    binding.likeButton.setOnClickListener {
      isCheck = if (!isCheck) {
        Toast.makeText(requireContext(),"Added To Favourite List",Toast.LENGTH_SHORT).show()
        if (!watchList!!.contains(data.title)) {
          // checking if the the title is not existed in the watchlist then add the title in it.
          watchList!!.add(data.title)
        }
        // calling the storedata function to add the current updated watchlist to shared prefs
        storeData()
        binding.likeButton.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
        true
      } else {
        Toast.makeText(requireContext(),"Removed To Favourite List",Toast.LENGTH_SHORT).show()
        // if pressed twice removing the title from the watchlist and updated the list in shared prefrences.
        // and also setting back the color of heart icon back to normal
        binding.likeButton.setColorFilter(
          ContextCompat.getColor(
            requireContext(),
            R.color.white
          )
        )
        watchList!!.remove(data.title)
        storeData()
        false
      }
    }
  }
  var watchList: ArrayList<String>? = null
  var isCheck = false

  // simple process to creating and storing data inside the sharedprefrence.
  private fun storeData() {
    val sharedPreferences =
      requireContext().getSharedPreferences("watchlist", Context.MODE_PRIVATE)
    val gson = Gson()
    val editor = sharedPreferences.edit()
    val json = gson.toJson(watchList)
    editor.putString("watchlist", json)
    editor.apply()
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