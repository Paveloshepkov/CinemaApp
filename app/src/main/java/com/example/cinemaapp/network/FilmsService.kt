package com.example.cinemaapp.network

import retrofit2.http.GET

interface FilmsService {
    @GET("films.json")
    suspend fun getMovieList(): FilmsContainer
}