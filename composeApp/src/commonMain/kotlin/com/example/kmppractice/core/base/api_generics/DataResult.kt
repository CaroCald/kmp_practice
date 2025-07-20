package com.example.kmppractice.core.base.api_generics


sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Loading(val isLoading: Boolean) : DataResult<Nothing>()
    data class Error(val exception: Throwable) : DataResult<Nothing>()
    data class ErrorGeneric<out T>(val data: T?, val exception: String): DataResult<Nothing>()

    data class Initial(val isLoading: Boolean) : DataResult<Nothing>()


}


sealed class CustomError : Exception() {
    data class NetworkError(override val message: String, val code: String) : CustomError()
    data class ServerError(override val message: String, val code: String) : CustomError()
    data class ClientError(override val message: String, val code: String) : CustomError()
    data class ValidationError(override val message: String, val code: String) : CustomError()
    data class TimeoutError(override val message: String, val code: String) : CustomError()
    data class RedirectError(override val message: String, val code: String) : CustomError()
    data class UnknownError(override val message: String, val code: String) : CustomError()
}