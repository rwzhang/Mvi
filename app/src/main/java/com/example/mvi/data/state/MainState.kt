package com.example.mvi.data.state

import com.example.mvi.data.bean.BaseBean
import com.example.mvi.data.bean.Data

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()

    data class Register(val loginResponse: BaseBean<Data>) :
        MainState()

    data class Error(val error: String) : MainState()


}