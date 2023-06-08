package com.example.tmdb_android_clone.models.credits

data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)