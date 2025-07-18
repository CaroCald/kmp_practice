package com.example.kmppractice.core.utils

import com.example.kmppractice.core.base.api_generics.DataResult
import com.example.kmppractice.core.base.api_generics.CustomError
object ErrorUtils {
    fun handleException(exception: Throwable): DataResult.Error {
        val customError = when (exception) {
            is IllegalArgumentException -> CustomError.ValidationError("Validation error: ${exception.message}", code = ErrorsCodes.ILLEGAL.code)
            is CustomError.ServerError -> CustomError.ValidationError("Validation error: ${exception.message}", code =exception.code)
            else -> CustomError.UnknownError("An unknown error occurred: ${exception.message}" , code = ErrorsCodes.UNKNOWN.code)
        }
        return DataResult.Error(customError)
    }
    enum class ErrorsCodes(val code: Int) {
        NETWORK(1),
        UNKNOWN(2),
        ILLEGAL(3);

        companion object {
            fun fromCode(code: Int): ErrorsCodes? {
                return entries.find { it.code == code }
            }
        }
    }
}