package com.dev.movieslist.view.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.movieslist.R
import com.develop.mygarage.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject


abstract class BaseActivity : AppCompatActivity(), BaseView {


    private val inputMethodManager: InputMethodManager by inject()
    protected val compositeDisposable: CompositeDisposable by inject()


    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getContentView())
        create(savedInstanceState)
    }

    open fun create(bundle: Bundle?) {}


    final override fun hideKeyboard() {
        currentFocus.let { inputMethodManager.hideSoftInputFromWindow(it!!.windowToken, 0) }

    }

    private fun enableView(view: View) = enableDisableView(view, true)

    private fun disableView(view: View) = enableDisableView(view, false)

    private fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {

            for (idx in 0 until view.childCount) {
                enableDisableView(view.getChildAt(idx), enabled)
            }
        }
    }

    private fun makeToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showError(error: String) {
        makeToast(error)
    }

    override fun showError() {
        makeToast(getString(R.string.error_occured))
    }

    override fun showNoConnection() {
        makeToast(getString(R.string.error_connection))
    }
}