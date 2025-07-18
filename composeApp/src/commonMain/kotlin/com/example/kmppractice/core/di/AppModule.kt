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
                    ignoreUnknownKeys = true // Good practice for robust clients
                    coerceInputValues = true
                })
        }
            defaultRequest {
                header( "Content-Type" , "application/json" )
                header( "Authorization" , "Bearer ${Constants.ACCESS_TOKEN}" )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
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