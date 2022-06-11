package com.example.cinemaapp.network

import com.example.cinemaapp.network.containers.FilmsContainer
import retrofit2.http.GET

interface FilmsService {
    @GET("films.json")
    suspend fun getMovieList(): FilmsContainer
}