package com.suilvapp.app.network

import android.util.Log
import com.example.mvi.data.bean.BaseBean
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MyCoroutineCallAdapterFactory private constructor():CallAdapter.Factory() {
    companion object{
        @JvmStatic
        @JvmName("create")
        operator fun  invoke()=MyCoroutineCallAdapterFactory()
    }
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
//        if (Deferred::class.java != getRawType(returnType)) {
//            return null
//        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == BaseBean::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Response must be parameterized as Response<Foo> or Response<out Foo>"
                )
            }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }
    private class BodyCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<T> {
            val deferred = CompletableDeferred<T>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    try {
                        deferred.completeExceptionally(t)
                    }catch (e:Throwable){
                        t.printStackTrace()
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        deferred.complete(response.body()!!)
                    } else {
                        if (response.errorBody() != null) {
                            if(response.code()==400||response.code()==401){
                                Log.d("zrw", "onResponse: "+response.message())
                            }else{
                                Log.d("zrw", "onResponse: "+response.code())
                            }
                        }
                    }
                }
            })
            return deferred
        }
    }

    private class ResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<Response<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<Response<T>> {
            val deferred = CompletableDeferred<Response<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                    Log.d("asdkghasdkjhf", "onFailure: 请求结果3")
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    deferred.complete(response)
                    Log.d("asdkghasdkjhf", "onResponse: 请求结果4")
                }
            })

            return deferred
        }
    }
}