package com.example.kmppractice.feature.movies.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kmppractice.core.base.api_generics.DataResult
import com.example.kmppractice.core.base.api_generics.GenericApiState
import com.example.kmppractice.core.utils.ErrorUtils
import com.example.kmppractice.feature.movies.data.MovieContent
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    
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
        viewModelScope.launch {
            try {
                _moviesState.value = DataResult.Loading(isLoading = true)
                moviesApiState = GenericApiState(isLoading = true)
                
                val movies = repository.fetchMovies()
                _moviesState.value = DataResult.Success(data = movies)
                moviesApiState = GenericApiState(isLoading = false)
            } catch (e: Exception) {
                val errorResult = ErrorUtils.handleException(e)
                _moviesState.value = errorResult
                // Extract the CustomError from the DataResult.Error
                val customError = when (errorResult) {
                    else -> errorResult.exception
                }
                moviesApiState = GenericApiState(isLoading = false, error = customError)
            }
        }
    }
    
    fun getMovieDetail(id: String) {
        // Prevent unnecessary API calls if already loaded
        if (_movieDetailState.value is DataResult.Success) return
        
        viewModelScope.launch {
            try {
                _movieDetailState.value = DataResult.Loading(isLoading = true)
                movieDetailApiState = GenericApiState(isLoading = true)
                
                val movieDetail = repository.getDetail(id)
                _movieDetailState.value = DataResult.Success(data = movieDetail)
                movieDetailApiState = GenericApiState(isLoading = false)
            } catch (e: Exception) {
                val errorResult = ErrorUtils.handleException(e)
                _movieDetailState.value = errorResult
                // Extract the CustomError from the DataResult.Error
                val customError = when (errorResult) {
                    else -> errorResult.exception
                }
                movieDetailApiState = GenericApiState(isLoading = false, error = customError)
            }
        }
    }
    
    fun refreshMovies() {
        fetchMovies()
    }
    
    fun clearMoviesError() {
        moviesApiState = GenericApiState()
    }
    
    fun refreshMovieDetail(id: String) {
        // Clear both the state and error before refreshing
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
}