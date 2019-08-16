package com.dev.movieslist.data.interactor

import com.dev.movieslist.app.API
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.network.ApiService
import org.koin.java.KoinJavaComponent
import retrofit2.Response
import retrofit2.Retrofit

class MovieDetailsInteractor : BaseDetailsInteractor<Response<Movie>>() {


    private var retrofit: Retrofit = KoinJavaComponent.getKoin().get()

    override fun get(id: String) = retrofit.create(ApiService.Movies::class.java)
        .getMovie(id, API.API_KEY)
        .compose(compose())!!
}