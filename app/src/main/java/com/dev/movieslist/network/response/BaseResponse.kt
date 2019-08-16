package com.dev.movieslist.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class BaseResponse<T>(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: T
) :
    Serializable