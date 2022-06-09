package com.example.cinemaapp.contract.model

import com.example.cinemaapp.repository.FilmsRepository
import com.example.cinemaapp.repository.FilmsRepositoryImpl

class ModelImpl : Model {
    override fun filmsRepository(): FilmsRepository {
        return FilmsRepositoryImpl()
    }
}