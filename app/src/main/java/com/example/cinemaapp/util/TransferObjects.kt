package com.example.cinemaapp.util

import com.example.cinemaapp.models.Film
import com.example.cinemaapp.models.FilmDetail
import com.example.cinemaapp.network.FilmsContainer

fun FilmsContainer.asDomainModel(): List<Film> {
    return films.map {
        Film(
            id = it.id,
            localizedName = it.localizedName,
            name = it.name,
            year = it.year,
            rating = it.rating,
            imageUrl = it.imageUrl,
            description = it.description,
            genres = it.genres
        )
    }
}

fun Film.asSerializable(): FilmDetail {
    return FilmDetail(
        id = id,
        localizedName = localizedName,
        name = name,
        year = year,
        rating = rating,
        imageUrl = imageUrl,
        description = description,
        genres = genres
    )
}
