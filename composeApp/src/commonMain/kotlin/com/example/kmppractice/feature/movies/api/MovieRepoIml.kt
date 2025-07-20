package com.example.kmppractice.feature.movies.api

import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.domain.MovieRepository
import com.example.kmppractice.feature.movies.data.MovieContent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.io.IOException

class MovieRepoIml(private val httpClient: HttpClient) : MovieRepository {

    override suspend fun fetchMovies(): MovieContent {
        return try {
            val movieContent = httpClient.get("${Constants.BASE_URL}/now_playing") {
                parameter("language", "en-US")
                parameter("page", 1)
            }.body<MovieContent>()
            
            if (movieContent.results.isNullOrEmpty()) {
                throw IOException("No movies found")
            }
            
            movieContent
        } catch (e: Exception) {
            when (e) {
                is IOException -> throw IOException("Network error: ${e.message}")
                else -> throw IOException("Request failed: ${e.message}")
            }
        }
    }

    override suspend fun getDetail(
        movieId: String,
        language: String
    ): MovieDetailContent {
        return try {
            val networkResponse = httpClient.get("${Constants.BASE_URL}/$movieId") {
               parameter("language", language)
                parameter("append_to_response", "credits,videos,images")
            }.body<MovieDetailContent>()
            
            if (networkResponse.title.isBlank()) {
                throw IOException("Movie not found")
            }
            
            networkResponse
        } catch (e: Exception) {
            when (e) {
                is IOException -> throw IOException("Network error: ${e.message}")
                else -> throw IOException("Request failed: ${e.message}")
            }
        }
    }

}