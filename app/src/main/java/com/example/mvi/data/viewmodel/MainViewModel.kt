package com.example.mvi.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi.intent.MainIntent
import com.example.mvi.data.repository.MainRepository
import com.example.mvi.data.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository= MainRepository()
    /**
     * 创建意图通道 无限大
     */
    val mainIntentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    /**
     * 可变状态数据流
     */
    private val _state = MutableStateFlow<MainState>(MainState.Idle)

    /**
     * 可观察状态数据流
     */
    val state: StateFlow<MainState> get() = _state

    init {
        viewModelScope.launch {
            mainIntentChannel.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.Register -> register()
                    else -> {}
                }
            }
        }
    }

    private fun register() {
        //启动一个协程
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Register(repository.register("zrw123123", "123123", "123123"))
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage ?: "UnKnown Error")
            }

        }
    }
}