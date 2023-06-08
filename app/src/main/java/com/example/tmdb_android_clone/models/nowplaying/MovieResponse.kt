package com.example.tmdb_android_clone.models.nowplaying

data class MovieResponse(
  val dates: Dates,
  val page: Int,
  val results: List<Result>,
  val total_pages: Int,
  val total_results: Int
)