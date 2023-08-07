package com.example.mvi.network

import com.example.mvi.data.bean.BannerBean
import com.example.mvi.data.bean.BaseBean
import com.example.mvi.data.bean.Data
import retrofit2.http.*


interface Api {

    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassowrd: String
    ): BaseBean<Data>

    @GET("banner/json")
    suspend fun getBanner():BaseBean<MutableList<BannerBean>>
}