package com.example.cinemaapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FilmsApi {
    private const val BASE_URL = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/"
    val retrofitService: FilmsService
        get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(FilmsService::class.java)
}