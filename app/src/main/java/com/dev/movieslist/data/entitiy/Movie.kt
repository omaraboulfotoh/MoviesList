package com.dev.movieslist.data.entitiy

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie(
    val id: String = "",
    val title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    @SerializedName("vote_average") val voteAverage: Float = 0f,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("release_date") val releaseDate: String = ""
) : Serializable