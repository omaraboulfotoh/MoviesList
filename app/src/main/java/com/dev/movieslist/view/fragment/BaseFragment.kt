package com.dev.movieslist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dev.movieslist.R
import com.dev.movieslist.view.activity.BaseActivity
import com.develop.mygarage.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment(), BaseView {

    lateinit var mView: View

    protected val compositeDisposable: CompositeDisposable by inject()

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        create(savedInstanceState)
    }

    open fun create(savedInstanceState: Bundle?) {}

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(getContentView(), container, false)
        createView(mView)
        return mView
    }

    open fun createView(view: View) {}


    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activityCreated(savedInstanceState)
    }

    open fun activityCreated(savedInstanceState: Bundle?) {}


    final override fun onResume() {
        super.onResume()
        resume()
    }

    open fun resume() {}


    override fun onPause() {
        super.onPause()
        pause()
    }

    open fun pause() {}


    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated(view, savedInstanceState)
    }

    open fun viewCreated(view: View?, savedInstanceState: Bundle?) {}


    override fun onDestroyView() {
        destroyView()
        super.onDestroyView()
    }

    open fun destroyView() {}


    final override fun onDestroy() {
        destroy()
        super.onDestroy()
    }

    open fun destroy() {}


    final override fun hideKeyboard() {
        (activity as BaseActivity).hideKeyboard()
    }

    private fun makeToast(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    override fun showError(error: String) {
        makeToast(error)
    }

    override fun showError() {
        makeToast(getString(R.string.error_occured))
    }

    override fun showNoConnection() {
        makeToast(getString(R.string.error_connection))
    }

    override fun getContentView(): Int {
        return -1;
    }
}