package com.example.mvi.data.repository

import com.suilvapp.app.network.RetrofitClient

class HomeRepository {
    suspend fun getBanner() =
        RetrofitClient.apiService.getBanner()

}