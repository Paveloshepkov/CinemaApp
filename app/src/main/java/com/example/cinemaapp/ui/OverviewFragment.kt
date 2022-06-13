package com.example.cinemaapp.ui

import android.annotation.SuppressLint
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
import com.example.cinemaapp.adapters.Item
import com.example.cinemaapp.adapters.CustomAdapter
import com.example.cinemaapp.adapters.decorations.GroupCardItemDecoration
import com.example.cinemaapp.adapters.decorations.GroupHorizontalItemDecoration
import com.example.cinemaapp.adapters.decorations.GroupVerticalItemDecoration
import com.example.cinemaapp.adapters.custom.FilmAdapter
import com.example.cinemaapp.adapters.custom.GenreAdapter
import com.example.cinemaapp.adapters.custom.TitleAdapter
import com.example.cinemaapp.contract.Contract
import com.example.cinemaapp.contract.model.ModelImpl
import com.example.cinemaapp.contract.presenter.FilmsPresenter
import com.example.cinemaapp.databinding.FragmentOverviewBinding
import com.example.cinemaapp.models.Film
import com.example.cinemaapp.models.Genre
import com.example.cinemaapp.models.Title
import com.example.cinemaapp.util.asSerializable

class OverviewFragment : Fragment(), Contract.View {

    private lateinit var presenter: Contract.Presenter

    private var _binding: FragmentOverviewBinding? = null
    private val binding
        get() = _binding!!

    private val genreTittle: MutableList<Item> by lazy {
        MutableList(1) { Title("Жанры") }
    }

    private val filmsTittle: MutableList<Item> by lazy {
        MutableList(1) { Title("Фильмы") }
    }

    private var genresListSize: Int = 0

    private lateinit var genreList: MutableList<Genre>
    private lateinit var filmList: MutableList<Film>

    private val genreTitleAdapter = CustomAdapter(listOf(TitleAdapter()))
    private val filmTitleAdapter = CustomAdapter(listOf(TitleAdapter()))

    private var genreAdapter = CustomAdapter(listOf(GenreAdapter(::onFilterClick)))
    private var filmAdapter = CustomAdapter(listOf(FilmAdapter(::onFilmClick)))

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

        with(binding.recyclerview) {
            layoutManager = gridLayoutManager
            addItemDecoration(GroupVerticalItemDecoration(R.layout.item_title, 8, 16))
            addItemDecoration(GroupHorizontalItemDecoration(R.layout.item_title, 32))
            addItemDecoration(GroupHorizontalItemDecoration(R.layout.item_genre, 32))
            addItemDecoration(GroupCardItemDecoration(R.layout.item_film, 130, 16, 8))
        }

        presenter.setGenresList()
        presenter.setFilmsList()

        binding.recyclerview.adapter = ConcatAdapter(
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build(),
            genreTitleAdapter,
            genreAdapter,
            filmTitleAdapter,
            filmAdapter
        )

        return binding.root
    }

    override fun displayOnViewGenres(genresList: MutableList<Genre>) {

        genreList = genresList
        genresListSize = genresList.size

        genreTitleAdapter.submitList(genreTittle.toList())
        genreAdapter.submitList(genresList.toList())
    }

    override fun displayOnViewFilms(filmsList: MutableList<Film>) {

        filmList = filmsList

        filmTitleAdapter.submitList(filmsTittle.toList())
        filmAdapter.submitList(filmsList.toList())

    }

    override fun displayError() {
        Toast.makeText(context, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        this.presenter = presenter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onFilterClick(genre: Genre) {
        if (genre.isFilter) {

            val genreIndex = genreList.indexOf(genre)
            val newItem = genre.copy(isFilter = genre.isFilter.not())

            genreList.removeAt(genreIndex)
            genreList.add(genreIndex, newItem)
            presenter.removeFilter()
            presenter.setFilmsList()
            genreAdapter.submitList(genreList.toList())
            genreAdapter.notifyDataSetChanged()
        } else {

            val genreIndex = genreList.indexOf(genre)
            val newItem = genre.copy(isFilter = genre.isFilter.not())

            genreList.removeAt(genreIndex)
            genreList.add(genreIndex, newItem)
            presenter.getFilter(newItem)
            genreList = presenter.setCheckedList()
            genreAdapter.submitList(genreList.toList())
            presenter.setFilmsList()
            genreAdapter.notifyDataSetChanged()
        }
    }

    private fun onFilmClick(film: Film) {
        val bundle = bundleOf("film" to film.asSerializable())
        this@OverviewFragment.findNavController()
            .navigate(R.id.action_overviewFragment_to_filmDetailFragment, bundle)
    }
}