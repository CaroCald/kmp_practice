package com.example.kmppractice.feature.movies.domain

import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.data.MovieContent

interface MovieRepository {

    suspend fun fetchMovies(): MovieContent

    suspend fun getDetail(
        movieId: String,
        language: String = "en-US"
    ): MovieDetailContent
}