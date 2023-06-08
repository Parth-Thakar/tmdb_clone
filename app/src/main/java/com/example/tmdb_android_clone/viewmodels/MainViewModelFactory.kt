package com.example.tmdb_android_clone.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tmdb_android_clone.repository.MovieRepository

// mainviewmodel factory class to create the paramitarized constructor of the ViewModel object.
class MainViewModelFactory(private val beerRepository: MovieRepository) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return MainViewModel(beerRepository) as T
  }
}