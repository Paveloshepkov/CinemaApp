package com.example.cinemaapp.adapters.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.cinemaapp.R
import com.example.cinemaapp.models.Title
import com.example.cinemaapp.databinding.ItemTitleBinding
import com.example.cinemaapp.adapters.BaseViewHolder
import com.example.cinemaapp.adapters.Item
import com.example.cinemaapp.adapters.ItemAdapter

class TitleAdapter : ItemAdapter<ItemTitleBinding, Title> {

    override fun isRelativeItem(item: Item) = item is Title

    override fun getLayoutId() = R.layout.item_title

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemTitleBinding, Title> {
        val binding = ItemTitleBinding.inflate(layoutInflater, parent, false)
        return TitleViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil
    private var diffUtil = object : DiffUtil.ItemCallback<Title>() {
        override fun areItemsTheSame(oldItem: Title, newItem: Title) =
            oldItem.title == oldItem.title

        override fun areContentsTheSame(oldItem: Title, newItem: Title) =
            oldItem == oldItem
    }

}

class TitleViewHolder(
    binding: ItemTitleBinding
) : BaseViewHolder<ItemTitleBinding, Title>(binding) {

    override fun onBind(item: Title) {
        super.onBind(item)
        binding.genresTitle.text = item.title
    }

}