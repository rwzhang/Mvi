package com.suilvapp.app.network

import com.example.mvi.network.Api
import com.example.mvi.network.NetworkInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    //    private const val BASE_URL = "http://192.168.1.225:8580/"
    private const val BASE_URL = "https://www.wanandroid.com/"
    private const val WeChatUrl = "https://api.weixin.qq.com/"//微信url
    private const val AMapUrl = "https://restapi.amap.com/"//高德地图

    //    private const val BASE_URL = "http://192.168.1.220:8000/"
    private val logging = HttpLoggingInterceptor()
    /**
     * 通过Moshi 将JSON转为为 Kotlin 的Data class
     */
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private var okHttpClient = OkHttpClient.Builder().apply {
        logging.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(logging)
        addNetworkInterceptor(NetworkInterceptor())
    }


    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient.build())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

        val apiService: Api =getRetrofit().create(Api::class.java)

}

