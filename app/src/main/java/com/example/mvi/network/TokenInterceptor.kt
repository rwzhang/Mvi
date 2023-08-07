package com.suilvapp.app.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

class TokenInterceptor : Interceptor {
    val HEADER_ACT_NAME = "Activity-Name" // 标记Activity界面名字

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        response.header(HEADER_ACT_NAME)
        val charset: Charset = Charset.forName("UTF-8")
        val responseBody = response.peekBody(Long.MAX_VALUE)
        val jsonReader: Reader = InputStreamReader(responseBody.byteStream(), charset)
        val reader = BufferedReader(jsonReader)
        val sbJson = StringBuilder()
        var line = reader.readLine()
        do {
            sbJson.append(line)
            line = reader.readLine()
        } while (line != null)
        Log.e("zrw", "response:本次请求 ")
        return response
    }
}