package com.dev.movieslist.di

import android.content.Context
import com.dev.movieslist.app.API.Companion.BASE_URL
import com.dev.movieslist.app.API.Companion.CONNECTION_TIMEOUT_SECONDS
import com.dev.movieslist.network.LoggingInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val networkModule = module {
    factory { gson() }
    factory { picasso(androidContext()) }
    factory { LoggingInterceptor() }
    factory { okHttpClient(get()) }
    factory { callFactory() }
    factory { converterFactory(get()) }
    factory { retrofit(get(), get(), get()) }
}

fun gson(): Gson {
    return GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .registerTypeAdapter(
            Date::class.java,
            JsonSerializer<Date> { src, _, _ -> JsonPrimitive(src?.time) })
        .create()
}

fun picasso(context: Context): Picasso {
    return Picasso.Builder(context)
        .build()
}


fun okHttpClient(loggingInterceptor: LoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()
}


fun callFactory(): CallAdapter.Factory {
    return RxJava2CallAdapterFactory.create()
}

fun converterFactory(gson: Gson): Converter.Factory {
    return GsonConverterFactory.create(gson)
}

fun retrofit(
    okHttpClient: OkHttpClient,
    callFactory: CallAdapter.Factory,
    converterFactory: Converter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(callFactory)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(converterFactory)
        .build()
}