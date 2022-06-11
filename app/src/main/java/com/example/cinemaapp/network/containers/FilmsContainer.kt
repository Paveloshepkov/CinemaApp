package com.example.cinemaapp.network.containers

import com.google.gson.annotations.SerializedName

data class FilmsContainer(
    @SerializedName("films") var films: ArrayList<FilmContainer> = arrayListOf()
)

