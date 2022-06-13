package com.example.cinemaapp.adapters.custom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.R
import com.example.cinemaapp.models.Genre
import com.example.cinemaapp.databinding.ItemGenreBinding
import com.example.cinemaapp.adapters.BaseViewHolder
import com.example.cinemaapp.adapters.Item
import com.example.cinemaapp.adapters.ItemAdapter

class GenreAdapter(
    private val onFilterClick: (Genre) -> Unit
) : ItemAdapter<ItemGenreBinding, Genre> {

    override fun isRelativeItem(item: Item) = item is Genre

    override fun getLayoutId() = R.layout.item_genre

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemGenreBinding, Genre> {
        val binding = ItemGenreBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding, onFilterClick)
    }

    override fun getDiffUtil() = diffUtil

    private var diffUtil = object : DiffUtil.ItemCallback<Genre>() {

        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) =
            oldItem.genre == newItem.genre

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem == newItem

        override fun getChangePayload(oldItem: Genre, newItem: Genre): Any? {
            if (oldItem.isFilter != newItem.isFilter) return newItem.isFilter
            return super.getChangePayload(oldItem, newItem)
        }

    }
}

class GenreViewHolder(
    binding: ItemGenreBinding,
    val onFilterClick: (Genre) -> Unit
) : BaseViewHolder<ItemGenreBinding, Genre>(binding) {

    init {
        binding.genre.setOnClickListener {
            if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onFilterClick(item)
        }
    }

    override fun onBind(item: Genre) {
        super.onBind(item)
        binding.genre.text = item.genre
        if(item.isFilter){
            binding.genre.setBackgroundColor(Color.parseColor("#82b1d3"))
        }else {
            binding.genre.setBackgroundColor(Color.parseColor("#424242"))
        }
    }

    override fun onBind(item: Genre, payloads: List<Any>) {
        super.onBind(item, payloads)
        val isFilter = payloads.last() as Boolean
        binding.genre.setFilter(isFilter)
    }

    private fun Button.setFilter(isFilter: Boolean) {
        if(isFilter){
            setBackgroundColor(Color.parseColor("#82b1d3"))
        }else {
            setBackgroundColor(Color.parseColor("#424242"))
        }
    }
}