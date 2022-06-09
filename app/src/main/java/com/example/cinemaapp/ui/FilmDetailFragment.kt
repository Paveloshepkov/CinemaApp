package com.example.cinemaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import coil.load
import com.example.cinemaapp.data.Film
import com.example.cinemaapp.databinding.FragmentFilmDetailBinding

class FilmDetailFragment : Fragment() {

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        val film: Film = arguments?.getSerializable("film") as Film
        (requireActivity() as AppCompatActivity).supportActionBar?.title = film.localizedName
        binding.apply {
            if (film.imageUrl != null) {
                val imgUri = film.imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
                poster.load(imgUri)
            }
            if (poster.drawable == null) {
                poster.isGone = true
            }
            title.text = film.name
            year.text = film.year.toString()
            if (film.rating != null) {
                rating.text = film.rating.toString()
            } else {
                ratingLabel.isGone = true
                rating.isGone = true
            }
            description.text = film.description
        }
        return binding.root
    }
}