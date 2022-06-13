package com.example.cinemaapp.repository

import com.example.cinemaapp.models.Film
import com.example.cinemaapp.network.FilmsApi
import com.example.cinemaapp.util.asDomainModel
import kotlinx.coroutines.runBlocking

class FilmsRepositoryImpl : FilmsRepository {

    override fun loadFilmsFromRepo(): List<Film> = runBlocking {
        return@runBlocking FilmsApi.retrofitService.getMovieList().asDomainModel()
    }
}