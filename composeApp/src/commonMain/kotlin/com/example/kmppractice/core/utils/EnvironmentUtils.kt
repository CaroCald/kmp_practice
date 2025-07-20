package com.example.kmppractice.core.utils

object EnvironmentUtils {
    
    /**
     * Get TMDB API token from environment variable
     * In production, implement platform-specific environment variable reading
     */
    fun getApiToken(): String {
        return try {
            // For now, we'll use a hardcoded token for testing
            // In production, you should implement platform-specific environment variable reading
            // For Android: BuildConfig or System.getenv()
            // For iOS: ProcessInfo.processInfo.environment
            // For Web: window.location.search or localStorage
            
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiM2Q0N2E1N2I4YTcyMjI2NTJiZmMwMmYwZmQxMmMxMyIsIm5iZiI6MTcyMjAyMTc5OS41NzYyOTUsInN1YiI6IjYyNDM3M2MyMGYzNjU1MDA5NjZhMmE2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8vASmrQDBX2E2x7R5FphwauSwA5ZC0UgvR9UBesvWuA"
        } catch (e: Exception) {
            println("Error reading environment variable: ${e.message}")
            "YOUR_API_TOKEN_HERE"
        }
    }
    
    /**
     * Check if API token is valid
     */
    fun isApiTokenValid(): Boolean {
        val token = getApiToken()
        return token.isNotBlank() && token != "YOUR_API_TOKEN_HERE"
    }
} 