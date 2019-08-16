package com.dev.movieslist.network

import com.dev.movieslist.app.API.Movies.Companion.MOVIE_DETAILS
import com.dev.movieslist.app.API.Movies.Companion.POPULAR
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.network.response.BaseResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The interface which provides methods to get result of webservices
 */
interface ApiService {

    interface Movies {
        @GET(POPULAR)
        fun getMoviesList(@Query("api_key") apiKey: String, @Query("page") page: Int):
                Observable<Response<BaseResponse<ArrayList<Movie>>>>

        @GET(MOVIE_DETAILS)
        fun getMovie(@Path("id") id: String, @Query("api_key") apiKey: String):
                Observable<Response<Movie>>

    }
}