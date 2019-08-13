package com.dev.movieslist.network.response

import java.io.Serializable

open class BaseResponse<T>(
    val success: Int = 0,
    val error: ArrayList<String> = ArrayList(),
    val data: T
) :
    Serializable