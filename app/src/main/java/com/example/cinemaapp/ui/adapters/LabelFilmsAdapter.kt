package com.example.cinemaapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaapp.databinding.LabelFilmsBinding

class LabelFilmsAdapter : RecyclerView.Adapter<LabelFilmsAdapter.FilmsLabelHolder>() {

    class FilmsLabelHolder(binding: LabelFilmsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsLabelHolder {
        return FilmsLabelHolder(
            LabelFilmsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FilmsLabelHolder, position: Int) {}

    override fun getItemCount() = 1
}



