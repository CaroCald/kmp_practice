package com.example.kmppractice.core.utils

import com.example.kmppractice.core.base.api_generics.CustomError
import com.example.kmppractice.core.base.api_generics.DataResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.io.IOException
import kotlinx.coroutines.TimeoutCancellationException

object ErrorUtils {
    
    fun handleException(exception: Throwable): DataResult.Error {
        return when (exception) {
            is IOException -> {
                DataResult.Error(
                    CustomError.NetworkError(
                        message = Constants.API_INTERNET_MESSAGE,
                        code = Constants.API_INTERNET_CODE
                    )
                )
            }
            is TimeoutCancellationException -> {
                DataResult.Error(
                    CustomError.TimeoutError(
                        message = "Request timeout. Please try again.",
                        code = "TIMEOUT"
                    )
                )
            }
            is ClientRequestException -> {
                DataResult.Error(
                    CustomError.ClientError(
                        message = "Invalid request: ${exception.message}",
                        code = exception.response.status.value.toString()
                    )
                )
            }
            is ServerResponseException -> {
                DataResult.Error(
                    CustomError.ServerError(
                        message = "Server error: ${exception.message}",
                        code = exception.response.status.value.toString()
                    )
                )
            }
            is RedirectResponseException -> {
                DataResult.Error(
                    CustomError.RedirectError(
                        message = "Redirect error: ${exception.message}",
                        code = exception.response.status.value.toString()
                    )
                )
            }
            else -> {
                DataResult.Error(
                    CustomError.UnknownError(
                        message = exception.message ?: Constants.API_SOMETHING_WENT_WRONG_MESSAGE,
                        code = Constants.API_FAILED_CODE
                    )
                )
            }
        }
    }
    
    fun getErrorMessage(exception: Throwable): String {
        return when (exception) {
            is IOException -> Constants.API_INTERNET_MESSAGE
            is TimeoutCancellationException -> "Request timeout. Please try again."
            is ClientRequestException -> "Invalid request. Please check your input."
            is ServerResponseException -> "Server error. Please try again later."
            is RedirectResponseException -> "Redirect error occurred."
            else -> exception.message ?: Constants.API_SOMETHING_WENT_WRONG_MESSAGE
        }
    }
    
    fun isNetworkError(exception: Throwable): Boolean {
        return exception is IOException || exception is TimeoutCancellationException
    }
    
    fun isServerError(exception: Throwable): Boolean {
        return exception is ServerResponseException
    }
    
    fun isClientError(exception: Throwable): Boolean {
        return exception is ClientRequestException
    }
}