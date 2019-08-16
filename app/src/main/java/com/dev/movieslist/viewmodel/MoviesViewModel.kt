package com.dev.movieslist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.data.entitiy.Resource
import com.dev.movieslist.data.entitiy.Status
import com.dev.movieslist.data.interactor.MovieDetailsInteractor
import com.dev.movieslist.data.interactor.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class MoviesViewModel
constructor(
    private val compositeDisposable: CompositeDisposable,
    private val movieDetailsInteractor: MovieDetailsInteractor,
    private val moviesInteractor: MoviesInteractor
) : ViewModel() {

    var pageIndex = 1
    var response: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    var detailsresponse: MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun loadLatest() {
        compositeDisposable.add(moviesInteractor
            .all(pageIndex)
            .doOnSubscribe { if (pageIndex == 1) response.postValue(Resource(Status.LOADING)) }
            .doOnComplete {
                pageIndex++
            }
            .subscribe({
                if (it.isSuccessful) {
                    response.postValue(Resource(Status.SUCCESS, it.body()!!.results))
                } else {
                    if (it is HttpException) {
                        response.postValue(
                            Resource(Status.ERROR, null, it.message())
                        )
                    }
                }
            }, {
                if (it is HttpException) {
                    response.postValue(
                        Resource(Status.ERROR, null, it.message())
                    )
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun getMovieDetails(id: String) {
        compositeDisposable.add(movieDetailsInteractor
            .get(id)
            .doOnSubscribe { }
            .doOnComplete {}
            .subscribe({
                if (it.isSuccessful) {
                    detailsresponse.postValue(Resource(Status.SUCCESS, it.body()!!))
                } else {
                    if (it is HttpException) {
                        detailsresponse.postValue(
                            Resource(Status.ERROR, null, it.message())
                        )
                    }
                }
            }, {
                if (it is HttpException) {
                    detailsresponse.postValue(
                        Resource(Status.ERROR, null, it.message())
                    )
                }
            })
        )
    }
}