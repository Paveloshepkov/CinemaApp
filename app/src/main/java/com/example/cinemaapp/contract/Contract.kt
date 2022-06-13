package com.example.cinemaapp.contract

import com.example.cinemaapp.contract.presenter.BasePresenter
import com.example.cinemaapp.contract.view.BaseView
import com.example.cinemaapp.models.Film
import com.example.cinemaapp.models.Genre

interface Contract {
    interface Presenter : BasePresenter {
        fun setInternetErrorStatus()
        fun setGenresList()
        fun setFilmsList()
        fun removeFilter()
        fun getFilter(genre: Genre)
        fun setCheckedList() : MutableList<Genre>
    }

    interface View : BaseView<Presenter> {
        fun displayOnViewGenres(genresList: MutableList<Genre>)
        fun displayOnViewFilms(filmsList: MutableList<Film>)
        fun displayError()
    }
}