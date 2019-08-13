package com.dev.movieslist.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.dev.movieslist.di.applicationModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        initDex()
    }

    private fun initDex() {
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        // koin
        startKoin(this, listOf(applicationModule))

    }

}