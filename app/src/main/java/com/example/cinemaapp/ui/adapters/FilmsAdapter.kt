package com.example.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinemaapp.R
import com.example.cinemaapp.data.Film
import com.example.cinemaapp.databinding.ItemFilmBinding

class FilmsAdapter(private val filmList: List<Film>, private val mListener: FilmsAdapterListener) :
    RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {

    interface FilmsAdapterListener {
        fun onFilmClick(film: Film)
    }

    class FilmsViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: Film) {
            if (film.imageUrl != null) {
                val imgUri = film.imageUrl!!.toUri().buildUpon().scheme("https").build()
                binding.filmPhoto.load(imgUri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_no_img)
                }
            } else {
                binding.filmPhoto.load(R.drawable.ic_no_img)
            }
            binding.filmItemTitle.text = film.localizedName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val filmItem = filmList[position]
        holder.bind(filmItem)
        holder.itemView.setOnClickListener { mListener.onFilmClick(filmItem) }
    }

    override fun getItemCount() = filmList.size
}