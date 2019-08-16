package com.dev.movieslist.data.interactor

import com.dev.movieslist.app.API
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.network.ApiService
import com.dev.movieslist.network.response.BaseResponse
import org.koin.java.KoinJavaComponent.getKoin
import retrofit2.Response
import retrofit2.Retrofit


class MoviesInteractor : BasePageInteractor<Response<BaseResponse<ArrayList<Movie>>>>() {
    private var retrofit: Retrofit = getKoin().get()


    override fun all(page: Int) =
        retrofit.create(ApiService.Movies::class.java)
            .getMoviesList(API.API_KEY, page)
            .compose(compose())!!

}