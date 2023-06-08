package com.example.tmdb_android_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb_android_clone.R
import com.example.tmdb_android_clone.databinding.SimilarMoviesItemBinding
import com.example.tmdb_android_clone.models.similar.Result

class SimilarMovieAdapter(val list : List<Result>,val context : Context):
  RecyclerView.Adapter<SimilarMovieAdapter.SimilarListViewHolder>()  {






  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarListViewHolder {
    return SimilarListViewHolder(
      LayoutInflater.from(context).inflate(R.layout.similar_movies_item, parent, false)
    )
  }

  override fun getItemCount(): Int {
    return list.size
 }

  override fun onBindViewHolder(holder: SimilarListViewHolder, position: Int) {
    val item = list[position]


    holder.binding.movieTitleTV.text= item.title

    Glide.with(context)
      .load("https://image.tmdb.org/t/p/original"+item.poster_path)
      .thumbnail(Glide.with(context).load(R.drawable.spinner))
      .into(holder.binding.movieImageView)

    holder.binding.languageTextView.text = "Original Language : "+item.original_language

  }


  inner class SimilarListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding = SimilarMoviesItemBinding.bind(view)
  }
}