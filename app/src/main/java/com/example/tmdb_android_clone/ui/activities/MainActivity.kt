package com.example.tmdb_android_clone.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.MovementMethod
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.databinding.ActivityMainBinding
import com.example.tmdb_android_clone.repository.MovieRepository
import com.example.tmdb_android_clone.retrofit.MovieApi
import com.example.tmdb_android_clone.retrofit.MovieApiHelper
import com.example.tmdb_android_clone.viewmodels.MainViewModel
import com.example.tmdb_android_clone.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
  lateinit var mainViewModel: MainViewModel

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val movieApi = MovieApiHelper.getInstance().create(MovieApi::class.java)
    val repository = MovieRepository(movieApi)
    mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)


    //setting up the navigation hosted fragment in the fragment space created in layout file
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentSpace)
    val navController = navHostFragment!!.findNavController()
    val popupmenu = PopupMenu(this, null)
    popupmenu.inflate(R.menu.bottom_nav_menu)
    binding.bottomBar.setupWithNavController(popupmenu.menu, navController)



  }
}