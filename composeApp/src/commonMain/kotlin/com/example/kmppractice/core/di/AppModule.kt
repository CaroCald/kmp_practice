package com.example.kmppractice.core.di

import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.feature.movies.api.MovieRepoIml
import com.example.kmppractice.feature.movies.domain.MovieRepository
import com.example.kmppractice.feature.movies.presentation.MoviesViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun AppModule() = module {

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
            
            defaultRequest {
                header("Content-Type", "application/json")

                // Try to get token from environment variable, fallback to a placeholder
                val token = getApiToken()
                if (token.isNotBlank() && token != "YOUR_API_TOKEN_HERE") {
                    header("Authorization", "Bearer $token")
                } else {
                    println("⚠️  Warning: TMDB API token not found. Please set TMDB_ACCESS_TOKEN environment variable.")
                    println("   Get your API token from: https://www.themoviedb.org/settings/api")
        }
    }
        }
    }

    single<MovieRepository> {
        MovieRepoIml(get())
    }

    factory {
        MoviesViewModel(get())
    }
}

private fun getApiToken(): String {
    return try {
        // For now, we'll use a hardcoded token for testing
        // In production, you should implement platform-specific environment variable reading
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiM2Q0N2E1N2I4YTcyMjI2NTJiZmMwMmYwZmQxMmMxMyIsIm5iZiI6MTcyMjAyMTc5OS41NzYyOTUsInN1YiI6IjYyNDM3M2MyMGYzNjU1MDA5NjZhMmE2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.8vASmrQDBX2E2x7R5FphwauSwA5ZC0UgvR9UBesvWuA"
    } catch (e: Exception) {
        println("Error reading environment variable: ${e.message}")
        "YOUR_API_TOKEN_HERE"
    }
}