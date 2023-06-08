package com.example.tmdb_android_clone.retrofit

import com.example.tmdb_android_clone.models.credits.MovieCredits
import com.example.tmdb_android_clone.models.details.MovieDetails
import com.example.tmdb_android_clone.models.nowplaying.MovieResponse
import com.example.tmdb_android_clone.models.reviews.MovieReviews
import com.example.tmdb_android_clone.models.similar.SimilarMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.cert.CertStoreSpi

interface MovieApi {

  //Api End Point to fetch all the movies
  @GET("3/movie/now_playing?api_key=74f9e36daf93daa3299c665fd9395d12&language=en-US&page=1")
  suspend fun getMovies() : Response<MovieResponse>

  // API end points to fetch the details of certain movie using movie ID
  @GET("3/movie/{movie_id}?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US")
  suspend fun getMovieSummary(@Path("movie_id") movieId : Int) : Response<MovieDetails>

  // API end Points to fetch the review about the certain movie using movie ID
  @GET("3/movie/{movie_id}/reviews?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US")
  suspend fun getMovieReview(@Path("movie_id") movieId : Int) : Response<MovieReviews>

  // API End points to fetch the Credits using Movie ID
  @GET("3/movie/{movie_id}/credits?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US")
  suspend fun getMovieCredits(@Path("movie_id") movieId : Int) : Response<MovieCredits>

  // API End points to fetch the Similar Movies using Movie ID
  @GET("3/movie/{movie_id}/similar?api_key=9d0e3e33437b228d3184927838d32b9b&language=en-US")
  suspend fun getMovieSimilar(@Path("movie_id") movieId : Int) : Response<SimilarMovies>


}