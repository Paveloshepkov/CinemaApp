package com.example.cinemaapp.models

import java.io.Serializable

data class FilmDetail(
    var id: Long? = null,
    var localizedName: String? = null,
    var name: String? = null,
    var year: Int? = null,
    var rating: Double? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    var genres: List<String> = arrayListOf()
) : Serializable