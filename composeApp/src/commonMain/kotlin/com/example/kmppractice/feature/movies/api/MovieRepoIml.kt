package com.example.kmppractice.feature.movies.api

import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.core.utils.safeApiGet
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.domain.MovieRepository
import com.example.kmppractice.feature.movies.data.MovieContent
import io.ktor.client.HttpClient

class MovieRepoIml(private val httpClient: HttpClient) : MovieRepository {
    override suspend fun fetchMovies(): MovieContent {
        return safeApiGet(
            httpClient = httpClient,
            url = "${Constants.BASE_URL}/now_playing",
            params = mapOf(
                "language" to "en-US",
                "page" to 1
            )
        )
    }

    override suspend fun getDetail(
        movieId: String,
        language: String
    ): MovieDetailContent {
        return safeApiGet(
            httpClient = httpClient,
            url = "${Constants.BASE_URL}/$movieId",
            params = mapOf(
                "language" to language,
                "append_to_response" to "credits,videos,images"
            )
        )
    }
}