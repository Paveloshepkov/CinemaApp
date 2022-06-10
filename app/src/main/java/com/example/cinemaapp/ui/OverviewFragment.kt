package com.example.cinemaapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.cinemaapp.R
import com.example.cinemaapp.contract.Contract
import com.example.cinemaapp.contract.model.ModelImpl
import com.example.cinemaapp.contract.presenter.FilmsPresenter
import com.example.cinemaapp.data.Film
import com.example.cinemaapp.databinding.FragmentOverviewBinding
import com.example.cinemaapp.ui.adapters.FilmsAdapter
import com.example.cinemaapp.ui.adapters.GenresAdapter
import com.example.cinemaapp.ui.adapters.LabelFilmsAdapter
import com.example.cinemaapp.ui.adapters.LabelGenresAdapter

class OverviewFragment : Fragment(), Contract.View {

    private lateinit var presenter: Contract.Presenter

    private var _binding: FragmentOverviewBinding? = null
    private val binding
        get() = _binding!!

    private var mGenresAdapter: GenresAdapter? = null
    private var mFilmsAdapter: FilmsAdapter? = null
    private var genresListSize: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        setPresenter(FilmsPresenter(this, ModelImpl()))

        presenter.setInternetErrorStatus()

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position > genresListSize + 1) 1 else 2
                }
            }
        binding.recyclerview.layoutManager = gridLayoutManager
        presenter.setGenresList()
        presenter.setFilmsList()
        return binding.root
    }

    override fun displayOnViewGenres(genresList: List<String>) {
        genresListSize = genresList.size
        mGenresAdapter = GenresAdapter(genresList, GenreItemClickListener())
    }

    override fun displayOnViewFilms(filmsList: List<Film>) {
        mFilmsAdapter = FilmsAdapter(filmsList, FilmItemClickListener())
        binding.recyclerview.adapter =
            ConcatAdapter(LabelGenresAdapter(), mGenresAdapter, LabelFilmsAdapter(), mFilmsAdapter)
    }

    override fun displayError() {
        Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        this.presenter = presenter
    }

    private inner class GenreItemClickListener : GenresAdapter.GenreAdapterListener {
        private var checkString = ""
        override fun onGenreClick(genre: String) {
            checkString =
                if (checkString != genre) {
                    presenter.setFilter(genre)
                    genre
                } else {
                    presenter.removeFilter()
                    ""
                }
            presenter.setFilmsList()
        }

        override fun isGenreWasClicked(): String {
            return checkString
        }
    }

    private inner class FilmItemClickListener : FilmsAdapter.FilmsAdapterListener {
        override fun onFilmClick(film: Film) {
            val bundle = bundleOf("film" to film)
            this@OverviewFragment.findNavController()
                .navigate(R.id.action_overviewFragment_to_filmDetailFragment, bundle)
        }
    }
}