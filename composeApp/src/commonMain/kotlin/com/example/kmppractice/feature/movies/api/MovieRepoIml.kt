package com.example.kmppractice.feature.movies.api

import com.example.kmppractice.core.utils.Constants.Companion.BASE_URL
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.domain.MovieRepository
import com.example.kmppractice.feature.movies.data.MovieContent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieRepoIml( private val httpClient: HttpClient) : MovieRepository{
    override suspend fun fetchMovies(): MovieContent {
        val movieContent = httpClient.get("$BASE_URL/now_playing").body<MovieContent>()
        return movieContent
    }

    override suspend fun getDetail(
        movieId: String,
        language: String
    ): MovieDetailContent {
        val networkResponse = httpClient.get("$BASE_URL/$movieId").body<MovieDetailContent>()
        return networkResponse
    }
}