package com.dev.movieslist.data.interactor

import io.reactivex.Observable
import retrofit2.Response

abstract class BaseDetailsInteractor<T : Response<*>> : BaseInteractor<T>() {

    abstract fun get(id: String): Observable<T>

}