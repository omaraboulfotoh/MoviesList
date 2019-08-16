package com.dev.movieslist.data.interactor

import io.reactivex.Observable
import retrofit2.Response



abstract class BasePageInteractor<T : Response<*>> : BaseInteractor<T>() {

    abstract fun all(page: Int): Observable<T>

}