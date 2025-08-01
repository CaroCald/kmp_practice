package com.example.kmppractice.feature.movies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kmppractice.core.base.api_generics.DataResult
import com.example.kmppractice.core.base.api_generics.GenericApiState
import com.example.kmppractice.core.network.launchApiCall
import com.example.kmppractice.feature.movies.data.MovieContent
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.use_case.MovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import com.example.kmppractice.feature.movies.data.Result

class MoviesViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    // Private state flows using DataResult
    private val _moviesState = MutableStateFlow<DataResult<MovieContent>>(DataResult.Initial(isLoading = false))
    private val _movieDetailState = MutableStateFlow<DataResult<MovieDetailContent>>(DataResult.Initial(isLoading = false))

    // Public state flows
    val moviesState: StateFlow<DataResult<MovieContent>> = _moviesState.asStateFlow()
    val movieDetailState: StateFlow<DataResult<MovieDetailContent>> = _movieDetailState.asStateFlow()

    // API state for UI - separate states for movies and movie detail
    var moviesApiState by mutableStateOf(GenericApiState())
        private set
    var movieDetailApiState by mutableStateOf(GenericApiState())
        private set

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launchApiCall(
            stateFlow = _moviesState,
            apiStateSetter = { moviesApiState = it },
            apiCall = { movieUseCase.fetchMovies() }
        )
    }

    fun getMovieDetail(id: String) {
        if (_movieDetailState.value is DataResult.Success) return
        viewModelScope.launchApiCall(
            stateFlow = _movieDetailState,
            apiStateSetter = { movieDetailApiState = it },
            apiCall = { movieUseCase.getMovieDetail(id) }
        )
    }

    fun refreshMovies() {
        fetchMovies()
    }

    fun clearMoviesError() {
        moviesApiState = GenericApiState()
    }

    fun refreshMovieDetail(id: String) {
        _movieDetailState.value = DataResult.Initial(isLoading = false)
        clearMovieDetailError()
        getMovieDetail(id)
    }

    fun clearMovieDetail() {
        _movieDetailState.value = DataResult.Initial(isLoading = false)
        movieDetailApiState = GenericApiState()
    }

    fun clearMovieDetailError() {
        movieDetailApiState = GenericApiState()
    }
    fun getMoviesList(): List<Result>? {
        return when (val state = _moviesState.value) {
            is DataResult.Success -> state.data.results
            else -> null
        }
    }

    fun searchMoviesByTitle(query: String): List<Result> {
        return movieUseCase.searchMoviesByTitle(getMoviesList(), query)
    }
}