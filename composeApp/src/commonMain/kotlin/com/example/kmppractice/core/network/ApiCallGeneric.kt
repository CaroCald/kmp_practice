package com.example.kmppractice.core.network

import com.example.kmppractice.core.base.api_generics.DataResult
import com.example.kmppractice.core.base.api_generics.GenericApiState
import com.example.kmppractice.core.utils.ErrorUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.launchApiCall(
    stateFlow: MutableStateFlow<DataResult<T>>,
    apiStateSetter: (GenericApiState) -> Unit,
    apiCall: suspend () -> T
) {
    this.launch {
        try {
            stateFlow.value = DataResult.Loading(isLoading = true)
            apiStateSetter(GenericApiState(isLoading = true))

            val result = apiCall()
            stateFlow.value = DataResult.Success(result)
            apiStateSetter(GenericApiState(isLoading = false))
        } catch (e: Exception) {
            val errorResult = ErrorUtils.handleException(e)
            stateFlow.value = errorResult
            val customError = errorResult.exception
            apiStateSetter(GenericApiState(isLoading = false, error = customError))
        }
    }
}
