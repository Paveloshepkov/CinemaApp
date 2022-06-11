package com.example.cinemaapp.network.containers

import com.google.gson.annotations.SerializedName

data class FilmContainer(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("localized_name") var localizedName: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("genres") var genres: ArrayList<String> = arrayListOf()
)
