package com.example.cinemaapp.repository

import com.example.cinemaapp.models.Film

interface FilmsRepository {
    fun loadFilmsFromRepo():List<Film>
}