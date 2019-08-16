package com.dev.movieslist.data.interactor

import android.util.Log
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseInteractor<T> {

    protected var TAG = javaClass.simpleName

    protected var idle: Boolean = false

    fun idle() = idle

    open fun compose(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it
                .doOnSubscribe { idle = false }
                .doOnComplete { idle = true }
                .doOnError { idle = true; Log.e(TAG, it.toString()) }
                .doOnDispose { idle = true }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
        }
    }

}