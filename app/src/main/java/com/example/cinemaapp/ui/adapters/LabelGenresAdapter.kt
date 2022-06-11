package com.example.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.databinding.LabelGenresBinding

class LabelGenresAdapter : RecyclerView.Adapter<LabelGenresAdapter.GenresLabelHolder>() {

    class GenresLabelHolder(binding: LabelGenresBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresLabelHolder {
        return GenresLabelHolder(
            LabelGenresBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GenresLabelHolder, position: Int) {}

    override fun getItemCount() = 1
}