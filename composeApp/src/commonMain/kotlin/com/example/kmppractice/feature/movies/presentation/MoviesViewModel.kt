package com.example.kmppractice.feature.movies.presentation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kmppractice.core.base.api_generics.DataResult
import com.example.kmppractice.core.base.api_generics.GenericApiState
import com.example.kmppractice.core.base.api_generics.baseEventApi
import com.example.kmppractice.core.utils.ErrorUtils
import com.example.kmppractice.feature.movies.data.MovieContent
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.feature.movies.data.Result
import com.example.kmppractice.feature.movies.domain.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieState = MutableStateFlow<DataResult<MovieContent>>(DataResult.Loading(false))
    private val _movieDetail= MutableStateFlow<DataResult<MovieDetailContent>>(DataResult.Loading(false))

    private var movieState: StateFlow<DataResult<MovieContent>> = _movieState.asStateFlow()
    private var movieDetailState: StateFlow<DataResult<MovieDetailContent>> = _movieDetail.asStateFlow()

    var apiState by mutableStateOf(GenericApiState())

    init {
        fetchMovies()
    }
    @Composable
    fun EventApi(onSuccess: @Composable () -> Unit, onError: () -> Unit){
        val event by movieState.collectAsState()
        apiState = baseEventApi(event =event , onSuccess = {
            onSuccess()
        }, onError = {
            onError()
        })
    }

    @Composable
    fun EventApiDetail(onSuccess: @Composable () -> Unit, onError: () -> Unit){
        val event by movieDetailState.collectAsState()
        apiState = baseEventApi(event =event , onSuccess = {
            onSuccess()
        }, onError = {
            onError()
        })
    }
    @Composable
    fun getResultSMovies(): List<Result>? {
        val moviesState by movieState.collectAsState()
        val valueRsp =  (moviesState as DataResult.Success).data.results
        print(valueRsp?.get(0)?.posterPath)
        return valueRsp
    }

    @Composable
    fun getDetailMovies(): MovieDetailContent {
        val movieState by movieDetailState.collectAsState()
        return  (movieState as DataResult.Success).data
    }

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                _movieState.value=DataResult.Loading(true)

                val expenses = repository.fetchMovies()
                _movieState.value= DataResult.Success(data = expenses)

            }catch (e: Exception){
                _movieState.value= ErrorUtils.handleException(e)
            }
        }
    }

    fun getMovieDetail(id:String) {
        viewModelScope.launch {
            try {
                val expenses = repository.getDetail(id)
                _movieDetail.value= DataResult.Success(data = expenses)

            }catch (e: Exception){
                _movieDetail.value= ErrorUtils.handleException(e)
            }
        }
    }


    fun restoreState() {
        apiState = GenericApiState()
        movieDetailState= MutableStateFlow<DataResult<MovieDetailContent>>(DataResult.Error(Throwable()))
        movieState= MutableStateFlow<DataResult<MovieContent>>(DataResult.Error(Throwable()))
    }
}