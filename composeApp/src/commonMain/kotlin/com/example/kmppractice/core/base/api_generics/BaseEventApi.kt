package com.example.kmppractice.core.base.api_generics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun <T> baseEventApi(
    event: DataResult<T>,
    onSuccess: @Composable (T) -> Unit,
    onError: (Throwable) -> Unit,
): GenericApiState {
    val apiState = remember { mutableStateOf(GenericApiState()) }
    when (event) {
        is DataResult.Error -> {
            val exception = event.exception
            apiState.value = apiState.value.copy(isLoading = false, error = exception)
            if (exception.message != null) {
                onError(exception)
            }
        }

        is DataResult.Initial -> {
            apiState.value = apiState.value.copy(isLoading = false, error = Throwable())
        }

        is DataResult.Loading -> {
            val isLoading = event.isLoading
            if (isLoading) {
                apiState.value = apiState.value.copy(isLoading = true, error = Throwable())
            }
        }

        is DataResult.Success -> {
            val result = event.data
            apiState.value = apiState.value.copy(isLoading = false)
            onSuccess(result)
        }

        is DataResult.ErrorGeneric<*> -> {
            //val err = event.data
            val errorMessage = ""
            val errorCode = 1
            apiState.value = apiState.value.copy(
                isLoading = false, error = CustomError.ServerError(
                    errorMessage,
                    errorCode
                )
            )
        }

    }
    return apiState.value
}


