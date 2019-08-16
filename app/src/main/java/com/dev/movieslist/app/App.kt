package com.dev.movieslist.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.dev.movieslist.di.applicationModule
import com.dev.movieslist.di.networkModule
import com.dev.movieslist.di.rxModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

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
        val mModules: MutableList<Module> = mutableListOf(
            networkModule,
            rxModule,
            applicationModule
        )
        startKoin {
            androidContext(this@App)
            modules(mModules)
        }

    }

}