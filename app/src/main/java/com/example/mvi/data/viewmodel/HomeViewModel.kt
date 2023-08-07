package com.example.mvi.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.data.intent.HomeIntent
import com.example.mvi.data.repository.HomeRepository
import com.example.mvi.data.state.HomeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository= HomeRepository()
    /**
     * 创建意图通道 无限大
     */
    val homeIntentChannel = Channel<HomeIntent>(Channel.UNLIMITED)

    /**
     * 可变状态数据流
     */
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)

    /**
     * 可观察状态数据流
     */
    val state: StateFlow<HomeState> get() = _state

    init {
        viewModelScope.launch {
            homeIntentChannel.consumeAsFlow().collect {
                when (it) {
                    is HomeIntent.Banner -> register()
                }
            }
        }
    }

    private fun register() {
        //启动一个协程
        viewModelScope.launch {
            _state.value = HomeState.Loading
            _state.value = try {
                HomeState.getBanner(repository.getBanner())
            } catch (e: Exception) {
                HomeState.Error(e.localizedMessage ?: "UnKnown Error")
            }

        }
    }
}