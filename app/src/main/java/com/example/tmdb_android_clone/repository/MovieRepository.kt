package com.example.tmdb_android_clone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdb_android_clone.models.credits.MovieCredits
import com.example.tmdb_android_clone.models.details.MovieDetails
import com.example.tmdb_android_clone.models.nowplaying.MovieResponse
import com.example.tmdb_android_clone.models.reviews.MovieReviews
import com.example.tmdb_android_clone.models.similar.SimilarMovies
import com.example.tmdb_android_clone.retrofit.MovieApi

class MovieRepository(private val movieApi : MovieApi) {

  // setting up the exposed LiveData.
  private val movieLiveData = MutableLiveData<MovieResponse>()
  val movieData : LiveData<MovieResponse>
    get() = movieLiveData


  // setting up the exposed LiveData.
  private val detailsLiveData = MutableLiveData<MovieDetails>()
  val movieDetailsData : LiveData<MovieDetails>
    get() = detailsLiveData


  // setting up the exposed LiveData.
  private val reviewsLiveData = MutableLiveData<MovieReviews>()
  val movieReviewsData : LiveData<MovieReviews>
    get() = reviewsLiveData


  // setting up the exposed LiveData.
  private val creditsLiveData = MutableLiveData<MovieCredits>()
  val movieCreditsData : LiveData<MovieCredits>
    get() = creditsLiveData


  // setting up the exposed LiveData.
  private val similarMoviesLiveData = MutableLiveData<SimilarMovies>()
  val similarMoviesData : LiveData<SimilarMovies>
    get() = similarMoviesLiveData




  // Suspend function to get the all the movies
  suspend fun getMovies(){
    val result = movieApi.getMovies()
    if(result?.body() != null)
    {
      movieLiveData.postValue(result.body())
    }
  }

  suspend fun getMoviesDetails(movieId : Int){
    val result = movieApi.getMovieSummary(movieId)

    if(result?.body()!=null)
    {
      detailsLiveData.postValue(result.body())
    }
  }

  suspend fun getMovieReview(movieId: Int)
  {
    val result = movieApi.getMovieReview(movieId)

    if(result?.body()!=null)
    {
      reviewsLiveData.postValue(result.body())
    }
  }


  suspend fun getMovieCredits(movieId: Int) {
    val result = movieApi.getMovieCredits(movieId)

    if (result?.body() != null)
    {
      creditsLiveData.postValue(result.body())
    }
  }

  suspend fun getSimilarMovies(movieId: Int) {
    val result = movieApi.getMovieSimilar(movieId)

    if (result?.body() != null)
    {
      similarMoviesLiveData.postValue(result.body())
    }
  }




}