package com.example.kmppractice

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform