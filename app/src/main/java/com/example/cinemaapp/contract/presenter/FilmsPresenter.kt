package com.example.cinemaapp.contract.presenter

import com.example.cinemaapp.contract.Contract
import com.example.cinemaapp.contract.model.Model
import com.example.cinemaapp.data.Film
import com.example.cinemaapp.repository.FilmsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilmsPresenter(view: Contract.View, model: Model) : Contract.Presenter {

    private val filmsRepository: FilmsRepository = model.filmsRepository()
    private var view: Contract.View? = view

    private var allFilmsList: List<Film> = listOf()
    private var selectedFilmsList: MutableList<Film>? = mutableListOf()
    private var genresList: MutableList<String>? = mutableListOf()
    private var eventNetworkError = false
    private var filmsFilterCheck = false
    private var filter: String = ""

    init {
        try {
            allFilmsList = filmsRepository.loadFilmsFromRepo()
            if (!allFilmsList.isNullOrEmpty()) {
                dataPreparation()
            } else {
                eventNetworkError = true
            }
        } catch (e: Exception) {
            eventNetworkError = true
        }
    }

    override fun setInternetErrorStatus() {
        if (eventNetworkError) {
            view?.displayError()
        }
    }

    override fun setGenresList() {
        if (!eventNetworkError) {
            genresList?.let { view?.displayOnViewGenres(it) }
        }
    }

    override fun setFilmsList() {
        if (!eventNetworkError) {
            if (filmsFilterCheck) {
                setSelectedFilms()
            } else {
                setFilms()
            }
        }
    }

    override fun setFilter(string: String) {
        filter = string
        filmsFilterCheck = true
    }

    override fun removeFilter() {
        filter = ""
        filmsFilterCheck = false
    }

    override fun onDestroy() {
        this.view = null
    }

    private fun dataPreparation() = runBlocking {
        launch {
            generatingListGenres(allFilmsList)
            sortFilmList(allFilmsList)
        }
    }

    private fun setFilms() {
        allFilmsList.let { view?.displayOnViewFilms(it) }
    }

    private fun setSelectedFilms() {
        filmsFilter(filter)
        selectedFilmsList.let {
            if (it != null) {
                view?.displayOnViewFilms(it)
            }
        }
    }

    private fun filmsFilter(string: String): List<Film> {
        selectedFilmsList = mutableListOf()
        for (element in allFilmsList) {
            if (element.genres.any { it == string }) {
                selectedFilmsList?.add(element)
            }
        }
        if (string == "") {
            selectedFilmsList = mutableListOf()
        }
        return selectedFilmsList as List<Film>
    }

    private fun generatingListGenres(filmsList: List<Film>): List<String> {

        val sortingGenreList: MutableList<String> = mutableListOf()

        for (element in filmsList) {
            for (i in element.genres) {
                sortingGenreList.add(i)
                for (j in sortingGenreList) {
                    if (!genresList?.contains(j)!!) {
                        genresList?.add(j)
                    }
                }
            }
        }
        return genresList as List<String>
    }

    private fun sortFilmList(filmsList: List<Film>): List<Film> {

        val mSortedFilms: MutableList<Film> = mutableListOf()
        val allLocalizedName: MutableList<String> = mutableListOf()
        for (element in filmsList) {
            allLocalizedName += element.localizedName.toString()
        }
        allLocalizedName.sort()
        for (i in allLocalizedName) {
            for (element in filmsList) {
                if (element.localizedName == i) {
                    mSortedFilms.add(
                        Film(
                            element.id,
                            element.localizedName,
                            element.name,
                            element.year,
                            element.rating,
                            element.imageUrl,
                            element.description,
                            element.genres
                        )
                    )
                }
            }
        }
        allFilmsList = mSortedFilms
        return allFilmsList
    }
}