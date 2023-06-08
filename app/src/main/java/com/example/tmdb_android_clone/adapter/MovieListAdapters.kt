package com.example.tmdb_android_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.databinding.MovieListItemBinding
import com.example.tmdb_android_clone.models.nowplaying.Result
import com.example.tmdb_android_clone.ui.fragments.FavouriteListFragmentDirections
import com.example.tmdb_android_clone.ui.fragments.HomeFragmentDirections

class MovListAdapters(val context: Context, var list: List<Result>,val fragType : String) :
  RecyclerView.Adapter<MovListAdapters.MovListViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovListViewHolder {
    return MovListViewHolder(
      LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
    )
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: MovListViewHolder, position: Int) {
    var item = list[position]


    holder.binding.movieTitleTV.text = item.title
    if(item.adult)
    {
      holder.binding.adultTextView.text = "Adult : YES"
    }
    else
    {
      holder.binding.adultTextView.text = "Adult : NO"
    }
    holder.binding.languageTextView.text = "Original Language : "+item.original_language

    holder.binding.releaseDateTextView.text = "Release Date : "+item.release_date

    holder.binding.bookButton.setOnClickListener {
      Toast.makeText(context,"${item.title} Movie Booked !",Toast.LENGTH_SHORT).show()
    }

    Glide.with(context)
      .load("https://image.tmdb.org/t/p/original"+item.poster_path)
      .thumbnail(Glide.with(context).load(R.drawable.spinner))
      .into(holder.binding.movieImageView)

    holder.itemView.setOnClickListener {

      if(fragType == "home")
      {
        findNavController(it).navigate(
          HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id)
        )
      }
      else
      {
        findNavController(it).navigate(
          FavouriteListFragmentDirections.actionFavouriteListFragmentToDetailsFragment(item.id)
        )
      }

    }


  }


  inner class MovListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding = MovieListItemBinding.bind(view)
  }

  }
