package com.example.mvi.data.bean


import com.squareup.moshi.Json

data class BannerBean(
    @Json(name = "desc")
    var desc: String,
    @Json(name = "id")
    var id: Int,
    @Json(name = "imagePath")
    var imagePath: String,
    @Json(name = "title")
    var title: String,
    @Json(name = "order")
    var order: Int,
    @Json(name = "type")
    var type: Int,
    @Json(name = "url")
    var url: String,
    @Json(name = "isVisible")
    var isVisible: Int
)