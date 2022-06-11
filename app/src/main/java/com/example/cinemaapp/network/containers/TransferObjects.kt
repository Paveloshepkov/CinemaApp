package com.example.cinemaapp.network.containers

import com.example.cinemaapp.data.Film

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