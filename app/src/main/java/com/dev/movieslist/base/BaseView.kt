package com.develop.mygarage.base

/**
 * Base view any view must implement.
 */
interface BaseView {
    fun showProgress()
    fun hideProgress()
    fun hideKeyboard()
    fun showError(error: String)
    fun showError()
    fun showNoConnection()
    fun getContentView(): Int
}