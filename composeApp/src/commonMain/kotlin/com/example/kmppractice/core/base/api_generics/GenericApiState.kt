package com.example.kmppractice.core.base.api_generics

data class GenericApiState(
    var isLoading: Boolean = false,
    var error: Throwable? = null
)