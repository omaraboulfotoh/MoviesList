package com.dev.movieslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.dev.movieslist.R
import com.dev.movieslist.app.API.Movies.Companion.IMAGES_URL
import com.dev.movieslist.base.BaseRecyclerAdapter
import com.dev.movieslist.base.BaseRecyclerViewHolder
import com.dev.movieslist.data.entitiy.Movie
import com.dev.movieslist.databinding.ItemMovieBinding
import com.develop.mygarage.base.ProgressViewHolder

class MoviesAdapter(movies: ArrayList<Movie>) : BaseRecyclerAdapter<Movie>(movies) {


    private var isProgress: Boolean = false

    companion object {
        const val ITEM_NORMAL = 0
        const val ITEM_PROGRESS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM_NORMAL) {
            val binding: ItemMovieBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_movie
                , parent, false
            )
            val holder = ViewHolder(binding)
            holder.setOnItemClickListener(itemClickListener)
            holder
        } else {
            ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.progress_item_movie,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        if (holder.itemViewType == ITEM_NORMAL)
            (holder as ViewHolder).bind(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].id.isEmpty()) ITEM_PROGRESS else ITEM_NORMAL
    }

    class ViewHolder(private val binding: ItemMovieBinding) : BaseRecyclerViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()

            binding.ivMovieImg.load(IMAGES_URL + movie.posterPath) {
                crossfade(true)
                placeholder(R.drawable.img_placeholder)
                transformations(RoundedCornersTransformation(20f))
            }
        }
    }

    fun showProgess() {
        data.add(Movie())
        data.add(Movie())
        data.add(Movie())
        data.add(Movie())
        data.add(Movie())
        data.add(Movie())
        isProgress = true
        notifyDataSetChanged()
    }

    fun hidePrgress() {
        if (isProgress) {
            isProgress = false
            data.clear()
            notifyDataSetChanged()
        }
    }
}