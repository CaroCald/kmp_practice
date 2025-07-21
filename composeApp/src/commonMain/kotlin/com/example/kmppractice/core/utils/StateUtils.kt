package com.example.kmppractice.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kmppractice.core.base.api_generics.DataResult
import kotlinx.coroutines.flow.StateFlow

/**
 * Composable extension to collect state and handle API results
 */
@Composable
fun <T> CollectApiState(
    stateFlow: StateFlow<DataResult<T>>,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (Throwable) -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onInitial: @Composable () -> Unit = {}
) {
    val state by stateFlow.collectAsState()
    
    when (val currentState = state) {
        is DataResult.Loading -> onLoading()
        is DataResult.Success -> onSuccess(currentState.data)
        is DataResult.Error -> onError(currentState.exception)
        is DataResult.ErrorGeneric<*> -> onError(Throwable(currentState.exception))
        is DataResult.Initial -> onInitial()
    }
} 