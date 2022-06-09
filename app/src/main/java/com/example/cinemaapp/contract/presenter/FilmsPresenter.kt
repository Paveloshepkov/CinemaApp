package com.example.cinemaapp.contract.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cinemaapp.contract.Contract
import com.example.cinemaapp.contract.model.Model
import com.example.cinemaapp.data.Film
import com.example.cinemaapp.repository.FilmsRepository
import java.util.*


class FilmsPresenter(view: Contract.View, model: Model) : Contract.Presenter {

    private lateinit var allFilmsList: List<Film>

    private var view: Contract.View? = view
    private val filmsRepository: FilmsRepository = model.filmsRepository()

    private var _eventNetworkError = MutableLiveData(false)
    private val eventNetworkError: LiveData<Boolean> get() = _eventNetworkError

    private var selectedFilmsList: MutableList<Film>? = LinkedList()
    private var genresList: MutableList<String>? = LinkedList()
    private var filmsFilterCheck = false
    private var filter: String = ""

    override fun getDataFromInternet() {
        val filmsList: List<Film>?
        try {
            filmsList = filmsRepository.loadFilmsFromRepo()
            if (!filmsList.isNullOrEmpty()) {
                generatingListGenres(filmsList)
                allFilmsList = filmsList
            } else {
                _eventNetworkError.value = true
            }
        } catch (e: Exception) {
            _eventNetworkError.value = true
        }
    }

    override fun setInternetErrorStatus() {
        if (eventNetworkError.value == true) {
            view?.displayError()
        }
    }

    override fun setGenresList() {
        if (!eventNetworkError.value!!) {
            genresList?.let { view?.displayOnViewGenres(it) }
        }
    }

    override fun setFilmsList() {
        if (!eventNetworkError.value!!) {
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

    private fun setSelectedFilms() {
        filmsFilter(filter)
        selectedFilmsList.let {
            if (it != null) {
                view?.displayOnViewFilms(it)
            }
        }
    }

    private fun setFilms() {
        allFilmsList.let { view?.displayOnViewFilms(it) }
    }

    private fun filmsFilter(string: String): List<Film> {
        selectedFilmsList = LinkedList()
        for (element in allFilmsList) {
            if (element.genres.any { it == string }) {
                selectedFilmsList?.add(element)
            }
        }
        if (string == "") {
            selectedFilmsList = LinkedList()
        }
        return selectedFilmsList as List<Film>
    }

    private fun generatingListGenres(filmsList: List<Film>): List<String> {

        var genresString: String? = ""
        for (element in filmsList) {
            genresString += element.genres.toString()
                .replace("[", " ")
                .replace("]", " ")
                .replace(",", " ")
        }

        val stringTokenizer = StringTokenizer(genresString, " ")

        while (stringTokenizer.hasMoreTokens()) {
            val buf = stringTokenizer.nextToken()
            if (!genresList?.contains(buf)!!)
                genresList?.add(buf)
        }
        return genresList as List<String>
    }
}