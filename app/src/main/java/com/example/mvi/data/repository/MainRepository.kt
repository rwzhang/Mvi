package com.example.mvi.data.repository

import com.suilvapp.app.network.RetrofitClient

class MainRepository{
    suspend fun register(userName:String,passWord:String,rePassWord:String)=
        RetrofitClient.apiService.register(userName,passWord,rePassWord)

}