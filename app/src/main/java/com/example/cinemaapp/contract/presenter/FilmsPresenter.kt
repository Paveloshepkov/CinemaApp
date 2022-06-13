package com.example.cinemaapp.contract.presenter

import com.example.cinemaapp.contract.Contract
import com.example.cinemaapp.contract.model.Model
import com.example.cinemaapp.models.Film
import com.example.cinemaapp.models.Genre
import com.example.cinemaapp.repository.FilmsRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilmsPresenter(view: Contract.View, model: Model) : Contract.Presenter {

    private val filmsRepository: FilmsRepository = model.filmsRepository()
    private var view: Contract.View? = view

    private var allFilmsList: MutableList<Film> = mutableListOf()
    private var selectedFilmsList: MutableList<Film> = mutableListOf()
    private var genresList: MutableList<Genre> = mutableListOf()

    private var eventNetworkError = false
    private var filmsFilterCheck = false

    private var filter = Genre("",false)

    init {
        getDataFromRepo()
    }

    private fun getDataFromRepo() = runBlocking {
        try {
            allFilmsList = filmsRepository.loadFilmsFromRepo() as MutableList<Film>

            if (!allFilmsList.isNullOrEmpty()) dataPreparation()

        } catch (e: Exception) {
            eventNetworkError = true
        }
    }

    override fun setInternetErrorStatus() {
        if (eventNetworkError) view?.displayError()
    }

    override fun setGenresList() {
        if (!eventNetworkError) genresList.let { view?.displayOnViewGenres(it) }
    }

    override fun setFilmsList() {
        if (!eventNetworkError) {
            if (filmsFilterCheck) setSelectedFilms() else setFilms()
        }
    }

    override fun getFilter(genre: Genre) {
            filter = genre
            filmsFilterCheck = true
    }

    override fun removeFilter(){
        filter = Genre("",false)
        filmsFilterCheck = false
    }

    override fun setCheckedList() : MutableList<Genre> {
        val checkingGenreList : MutableList<Genre> = mutableListOf()
        for (i in genresList)
        {
            if(i.genre == filter.genre){
                i.isFilter = true
                checkingGenreList.add(i)
            } else {
                i.isFilter = false
                checkingGenreList.add(i)
            }

        }
        genresList = checkingGenreList

        return genresList
    }



    override fun onDestroy() {
        this.view = null
    }

    private fun setFilms() {
        allFilmsList.let { view?.displayOnViewFilms(it) }
    }

    private fun setSelectedFilms() {
        filmsFilter(filter)
        selectedFilmsList.let {
            view?.displayOnViewFilms(it)
        }
    }

    private fun filmsFilter(genre: Genre): List<Film> {
        selectedFilmsList = mutableListOf()
        for (element in allFilmsList) {
            if (element.genres.any { it == genre.genre }) {
                selectedFilmsList.add(element)
            }
        }
        if (genre.genre == "") selectedFilmsList = mutableListOf()

        return selectedFilmsList
    }



    private fun dataPreparation() = runBlocking {
        launch {
            sortFilmList(allFilmsList)
            generatingListGenres(allFilmsList)
        }
    }

    private fun generatingListGenres(filmsList: List<Film>): MutableList<Genre> {

        val sortingGenreList: MutableList<Genre> = mutableListOf()

        for (element in filmsList) {
            for (i in element.genres) {
                sortingGenreList.add(Genre(i,false))
                for (j in sortingGenreList) {
                    if (!genresList.contains(j)) {
                        genresList.add(j)
                    }
                }
            }
        }
        return genresList
    }

    private fun sortFilmList(filmsList: List<Film>) {

        val allNames: MutableList<String> = mutableListOf()
        val sortedFilms: MutableList<Film> = mutableListOf()

        for (element in filmsList) {
            allNames += element.localizedName.toString()
        }
        allNames.sort()
        for (i in allNames) {
            for (element in filmsList) {
                if (element.localizedName == i) {
                    sortedFilms.add(element)
                }
            }
        }
        allFilmsList = sortedFilms
    }
}