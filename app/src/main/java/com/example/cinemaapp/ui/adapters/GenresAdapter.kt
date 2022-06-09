package com.example.cinemaapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.databinding.ItemGenreBinding

class GenresAdapter(
    private val genresList: List<String>,
    private val mClickListener: GenreAdapterListener
) : RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    interface GenreAdapterListener {
        fun onGenreClick(genre: String)
        fun isGenreWasClicked(): String
    }

    class GenresViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: String) {
            binding.genre.text = genre
        }

        fun bindColor(genreWasClicked: String) {
            if (binding.genre.text == genreWasClicked) {
                binding.genre.setBackgroundColor(Color.parseColor("#82b1d3"))
            } else {
                binding.genre.setBackgroundColor(Color.parseColor("#424242"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {

        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genreItem = genresList[position]

        holder.apply {
            bind(genreItem)
            itemView.setOnClickListener { mClickListener.onGenreClick(genreItem) }
            bindColor(mClickListener.isGenreWasClicked())
        }
    }

    override fun getItemCount() = genresList.size
}