package com.example.cinemaapp.contract

import com.example.cinemaapp.contract.presenter.BasePresenter
import com.example.cinemaapp.contract.view.BaseView
import com.example.cinemaapp.data.Film

interface Contract {
    interface Presenter : BasePresenter {
        fun getDataFromInternet()
        fun setInternetErrorStatus()
        fun setGenresList()
        fun setFilmsList()
        fun setFilter(string: String)
        fun removeFilter()
    }

    interface View : BaseView<Presenter> {
        fun displayOnViewGenres(genresList: List<String>)
        fun displayOnViewFilms(filmsList: List<Film>)
        fun displayError()
    }
}