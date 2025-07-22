package com.example.kmppractice.feature.movies.use_case


import com.example.kmppractice.feature.movies.data.MovieContent
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.domain.MovieRepository
import kotlinx.coroutines.flow.Flow

import com.example.kmppractice.feature.movies.data.Result

/**
 * Use case for movie-related operations
 * Provides business logic for movie data management
 */
class MovieUseCase (
    private val movieRepository: MovieRepository
) {

    /**
     * Fetch movies from the repository
     * @param context Application context for network operations
     * @return Flow of movie content result
     */
    suspend fun fetchMovies(): MovieContent {
        return movieRepository.fetchMovies()
    }

    /**
     * Get movie details by ID
     * @param movieId The ID of the movie to fetch details for
     * @param context Application context for network operations
     * @return Flow of movie detail content result
     */
    suspend fun getMovieDetail(movieId: String): MovieDetailContent {
        return movieRepository.getDetail(movieId)
    }

    /**
     * Get filtered movies by genre
     * @param movies List of movies to filter
     * @param genre Genre to filter by
     * @return Filtered list of movies
     */
    fun getMoviesByGenre(movies: List<Result>?, genre: String): List<Result> {
        val genreId = genre.toLongOrNull() ?: return emptyList()
        return movies?.filter { movie ->
            movie.genreIDS?.contains(genreId) == true
        } ?: emptyList()
    }

    /**
     * Get movies sorted by rating
     * @param movies List of movies to sort
     * @param ascending Whether to sort in ascending order (default: false for highest first)
     * @return Sorted list of movies
     */
    fun getMoviesSortedByRating(movies: List<Result>?, ascending: Boolean = false): List<Result> {
        return movies?.sortedBy { movie ->
            if (ascending) movie.voteAverage ?: 0.0 else -(movie.voteAverage ?: 0.0)
        } ?: emptyList()
    }

    /**
     * Get movies sorted by release date
     * @param movies List of movies to sort
     * @param ascending Whether to sort in ascending order (default: false for newest first)
     * @return Sorted list of movies
     */
    fun getMoviesSortedByDate(movies: List<Result>?, ascending: Boolean = false): List<Result> {
        return movies?.sortedBy { movie ->
            val dateString = movie.releaseDate ?: ""
            if (ascending) dateString else dateString.reversed()
        } ?: emptyList()
    }

    /**
     * Search movies by title
     * @param movies List of movies to search in
     * @param query Search query
     * @return Filtered list of movies matching the query
     */
    fun searchMoviesByTitle(movies: List<Result>?, query: String): List<Result> {
        if (query.isBlank()) return movies ?: emptyList()
        
        return movies?.filter { movie ->
            movie.title?.contains(query, ignoreCase = true) == true ||
            movie.originalTitle?.contains(query, ignoreCase = true) == true
        } ?: emptyList()
    }

    /**
     * Get popular movies (high vote count)
     * @param movies List of movies to filter
     * @param threshold Minimum vote count threshold (default: 1000)
     * @return List of popular movies
     */
    fun getPopularMovies(movies: List<Result>?, threshold: Long = 1000): List<Result> {
        return movies?.filter { movie ->
            (movie.voteCount ?: 0) >= threshold
        } ?: emptyList()
    }
} 