package com.example.spacexclient_spirin.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected val _state = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val state: StateFlow<ScreenState> = _state

    override val coroutineContext: CoroutineContext = exceptionHandler

    abstract val exceptionHandler: CoroutineExceptionHandler

    sealed class ScreenState {
        class Success<T>(var list: List<T>) : ScreenState()
        data object Loading : ScreenState()
        data object Error : ScreenState()
    }

}