package com.dev.movieslist.view.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.movieslist.R
import com.dev.movieslist.adapter.MoviesAdapter
import com.dev.movieslist.data.entitiy.Status
import com.dev.movieslist.utils.helpers.OnItemClickListener
import com.dev.movieslist.viewmodel.MoviesViewModel
import com.dev.movieslist.widgets.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), OnItemClickListener {

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var mAdapter: MoviesAdapter

    override fun getContentView() = R.layout.activity_main

    override fun create(bundle: Bundle?) {
        super.create(bundle)
        initRecyclerView()
        initObservables()
        viewModel.loadLatest()
    }

    private fun initRecyclerView() {
        mAdapter = MoviesAdapter(ArrayList())
        mAdapter.setOnItemClickListener(this)
        val linearLayoutManager = LinearLayoutManager(this)
        with(rvMovies) {
            layoutManager = linearLayoutManager
            adapter = mAdapter

            addOnScrollListener(object : EndlessScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
//                    viewModel.loadLatest()
                }
            })
        }
    }

    private fun initObservables() {
        viewModel.response.observe(this,
            Observer {
                when (it!!.status) {
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        hideProgress()
                        it.data?.let { it1 -> mAdapter.insertAll(mAdapter.itemCount, it1) }
                    }
                    Status.ERROR -> {
                        hideProgress()
                    }
                }
            })
    }

    override fun onItemClick(view: View?, position: Int) {}

    override fun showProgress() {
        mAdapter.showProgess()
    }

    override fun hideProgress() {
        mAdapter.hidePrgress()
    }

}