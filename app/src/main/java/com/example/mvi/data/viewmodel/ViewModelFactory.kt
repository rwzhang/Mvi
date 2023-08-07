package com.example.mvi.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvi.network.Api
import com.example.mvi.data.repository.MainRepository

/**
 * ViewModel工厂
 */
class ViewModelFactory(private val apiService: Api) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // 判断 MainViewModel 是不是 modelClass 的父类或接口
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("UnKnown class")
    }
}