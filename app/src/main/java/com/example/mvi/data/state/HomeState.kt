package com.example.mvi.data.state

import com.example.mvi.data.bean.BannerBean
import com.example.mvi.data.bean.BaseBean

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()

    data class getBanner(val loginResponse: BaseBean<MutableList<BannerBean>>) :
        HomeState()

    data class Error(val error: String) : HomeState()
}