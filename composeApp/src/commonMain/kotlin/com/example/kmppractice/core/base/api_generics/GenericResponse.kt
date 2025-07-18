package com.example.kmppractice.core.base.api_generics
import kotlinx.serialization.SerialName

open class GenericResponse<T>(
    @SerialName("status_code")
    val code: Int? = null,
    val responseType: String? = null,
    @SerialName("status_message")
    val message: String? = null,
    val content: T? = null
)