package com.example.cinemaapp.repository

import com.example.cinemaapp.data.Film

interface FilmsRepository {
    fun loadFilmsFromRepo():List<Film>
}