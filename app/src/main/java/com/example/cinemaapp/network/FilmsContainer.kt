package com.example.cinemaapp.network

import com.example.cinemaapp.network.FilmContainer
import com.google.gson.annotations.SerializedName

data class FilmsContainer(
    @SerializedName("films") var films: ArrayList<FilmContainer> = arrayListOf()
)

