package com.dev.movieslist.app

import android.graphics.Color

/** The base URL of the API */

public val ColorPrimary: Int = Color.parseColor("#008577")

class API {
    companion object {
        const val CONNECTION_TIMEOUT_SECONDS = 30L
        const val API_KEY = "b7c5d0254c58b280e5c1bf91c3bd589e"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }


    class Movies {
        companion object {
            const val MOVIE = "movie"
            const val POPULAR = "$MOVIE/popular"
            const val MOVIE_DETAILS = "$MOVIE/{id}"
            const val IMAGES_URL = "https://image.tmdb.org/t/p/original"
        }
    }

}