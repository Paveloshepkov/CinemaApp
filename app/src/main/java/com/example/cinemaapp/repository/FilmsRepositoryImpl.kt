package com.example.cinemaapp.repository

import com.example.cinemaapp.data.Film
import com.example.cinemaapp.network.FilmsApi
import com.example.cinemaapp.network.containers.asDomainModel
import kotlinx.coroutines.runBlocking

class FilmsRepositoryImpl : FilmsRepository {

    override fun loadFilmsFromRepo(): List<Film> = runBlocking {
        return@runBlocking FilmsApi.retrofitService.getMovieList().asDomainModel()
    }
}