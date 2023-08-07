package com.example.mvi.network;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 网络过滤器
 * @author zhangrenwei
 */
public class NetworkInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Request request = chain.request();
        Response response = chain.proceed(request);
        String responseBody = null;
        String responseCode = null;
        String url = null;
        String requestBody = null;
        try {
            url = request.url().toString();
            requestBody = getRequestBody(request);
            responseBody = Objects.requireNonNull(response.body()).string();
            responseCode = String.valueOf(response.code());
            MediaType mediaType = Objects.requireNonNull(response.body()).contentType();
            response = response.newBuilder().body(ResponseBody.create(mediaType, responseBody)).build();
        } catch (Exception e) {
            Log.d("response",e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            String duration = String.valueOf(end - start);
            Log.d("response","请求结果:responseTime= {" + duration + "}, requestUrl= {" + url + "}, params={" + requestBody + "}, responseCode= {" + responseCode + "}, result= {" + responseBody + "}");
        }

        return response;
    }

    private String getRequestBody(Request request) {
        String requestContent = "";
        if (request == null) {
            return requestContent;
        }
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return requestContent;
        }
        try {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = StandardCharsets.UTF_8;
            requestContent = buffer.readString(charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestContent;
    }
}
