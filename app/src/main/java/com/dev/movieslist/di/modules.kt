package com.dev.movieslist.di

import com.dev.movieslist.data.interactor.MoviesInteractor
import com.dev.movieslist.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module(override = true) {
    factory { MoviesInteractor() }
    viewModel { MoviesViewModel(get(), get()) }
}