package com.example.mvi.data.bean

import com.squareup.moshi.Json


data class Data(
//    var admin: Boolean,
//    var chapterTops: List<Any>,
//    var coinCount: Int,
//    var collectIds: List<Any>,
//    var email: String,
//    var icon: String,
    @Json( name = "id")
    val id: Int,
//    var nickname: String,
//    var password: String,
//    var publicName: String,
//    var token: String,
//    var type: Int,
//    var username: String
)