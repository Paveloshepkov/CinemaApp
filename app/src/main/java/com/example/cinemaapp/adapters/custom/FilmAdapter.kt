package com.example.cinemaapp.adapters.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cinemaapp.R
import com.example.cinemaapp.models.Film
import com.example.cinemaapp.databinding.ItemFilmBinding
import com.example.cinemaapp.adapters.BaseViewHolder
import com.example.cinemaapp.adapters.Item
import com.example.cinemaapp.adapters.ItemAdapter

class FilmAdapter(
    private val onFilmClick: (Film) -> Unit
) : ItemAdapter<ItemFilmBinding, Film> {

    override fun isRelativeItem(item: Item) = item is Film

    override fun getLayoutId() = R.layout.item_film

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemFilmBinding, Film> {
        val binding = ItemFilmBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(binding, onFilmClick)
    }

    override fun getDiffUtil() = diffUtil

    private var diffUtil = object : DiffUtil.ItemCallback<Film>() {

        override fun areItemsTheSame(oldItem: Film, newItem: Film) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Film, newItem: Film) = oldItem == newItem

    }
}

class FilmViewHolder(
    binding: ItemFilmBinding,
    val onFilmClick: (Film) -> Unit
) : BaseViewHolder<ItemFilmBinding, Film>(binding) {

    init {
        binding.filmPhoto.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onFilmClick(item)
        }
    }

    override fun onBind(item: Film) {
        super.onBind(item)
        if (item.imageUrl != null) {
            val imgUri = item.imageUrl!!.toUri().buildUpon().scheme("https").build()
            binding.filmPhoto.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_no_img)
            }
        } else {
            binding.filmPhoto.load(R.drawable.ic_no_img)
        }
        binding.filmItemTitle.text = item.localizedName
    }
}
