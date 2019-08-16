package com.dev.movieslist.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import coil.api.load
import com.dev.movieslist.app.API
import com.dev.movieslist.app.ColorPrimary
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.data.entitiy.Status
import com.dev.movieslist.databinding.ActivityMovieDetailsBinding
import com.dev.movieslist.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.view_background.*
import org.koin.android.viewmodel.ext.android.viewModel


class MovieDetailsActivity : BaseActivity() {

    private val viewModel: MoviesViewModel by viewModel()
    lateinit var mainBinding: ActivityMovieDetailsBinding

    companion object {
        private const val KEY_MOVIE = "movie"

        fun start(activity: Activity, movie: Movie) {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra(KEY_MOVIE, movie)
            activity.startActivity(intent)
        }
    }

    override fun getContentView() = com.dev.movieslist.R.layout.activity_movie_details

    override fun create(bundle: Bundle?) {
        super.create(bundle)
        mainBinding =
            DataBindingUtil.setContentView(this, com.dev.movieslist.R.layout.activity_movie_details)
        val movie = intent.getSerializableExtra(KEY_MOVIE) as Movie
        initObservables()
        initToolbar()
        showDetails(movie)
        viewModel.getMovieDetails(movie.id)
    }

    private fun initObservables() {
        viewModel.detailsresponse.observe(this,
            Observer {
                when (it!!.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        it.data?.let { it1 -> showDetails(it1) }
                    }
                    Status.ERROR -> {
                        showError()
                    }
                }
            })
    }

    private fun initToolbar() {
        collapsingToolbarLayout.setContentScrimColor(ColorPrimary)
        collapsingToolbarLayout.setStatusBarScrimColor(ColorPrimary)
        toolbar.getChildAt(0).setOnClickListener { onBackPressed() }
    }

    private fun showDetails(movie: Movie) {

        mainBinding.movie = movie
        mainBinding.executePendingBindings()
        ivMovieImg.load(API.Movies.IMAGES_URL + movie.backdropPath) {
            crossfade(true)
        }
    }
}
