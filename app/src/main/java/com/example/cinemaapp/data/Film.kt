package com.example.cinemaapp.data

import java.io.Serializable
import kotlin.String

data class Film(
    var id: Long? = null,
    var localizedName: String? = null,
    var name: String? = null,
    var year: Int? = null,
    var rating: Double? = null,
    var imageUrl: String? = null,
    var description: String? = null,
    var genres: List<String> = arrayListOf()
) : Serializable
