package com.example.tmdb_android_clone.retrofit

import com.example.tmdb_android_clone.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieApiHelper {

  fun getInstance() : Retrofit {
    return Retrofit.Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

}