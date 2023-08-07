package com.example.mvi.data.bean

import com.squareup.moshi.Json

open class BaseBean<T> (
    @Json(name="errorCode")
    val errorCode:Int,
    @Json(name = "errorMsg")
    val errorMsg:String,
    @Json(name="data")
    val data :T?=null)
