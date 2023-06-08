package com.example.tmdb_android_clone.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb_android_clone.models.credits.MovieCredits
import com.example.tmdb_android_clone.models.details.MovieDetails
import com.example.tmdb_android_clone.models.nowplaying.MovieResponse
import com.example.tmdb_android_clone.models.reviews.MovieReviews
import com.example.tmdb_android_clone.models.similar.SimilarMovies
import com.example.tmdb_android_clone.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MovieRepository) : ViewModel()  {

  init {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getMovies()
    }
  }

  // Exposed livedata for the all movie UI
  val movies : LiveData<MovieResponse>
    get() = repository.movieData


  fun getMovieDetails(movieId : Int){
    viewModelScope.launch {
      repository.getMoviesDetails(movieId)
    }
  }

  // Exposed livedata for the movie details UI
  val moviesDetails : LiveData<MovieDetails>
    get() = repository.movieDetailsData



  fun getMovieReviews(movieId: Int)
  {
    viewModelScope.launch {
      repository.getMovieReview(movieId)
    }
  }

  val movieReviewsLiveData : LiveData<MovieReviews>
    get() = repository.movieReviewsData


  fun getMovieCredits(movieId: Int)
  {
    viewModelScope.launch {
      repository.getMovieCredits(movieId)
    }
  }

  val movieCreditsLiveData : LiveData<MovieCredits>
    get() = repository.movieCreditsData


  fun getSimilarMovies(movieId: Int)
  {
    viewModelScope.launch {
      repository.getSimilarMovies(movieId)
    }
  }

  val similarMoviesLiveData : LiveData<SimilarMovies>
    get() = repository.similarMoviesData


}