package com.example.cinemaapp.contract.model

import com.example.cinemaapp.repository.FilmsRepository

interface Model {
    fun filmsRepository(): FilmsRepository
}