package com.example.kmppractice.feature.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.input.CustomSearchBar
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.shared.ImageCard
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.navigation.NavigationItem
import com.example.kmppractice.core.utils.CollectApiState
import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.feature.movies.data.Result
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MovieWebScreen(
    navController: Navigator,
    moviesViewModel: MoviesViewModel
) {

    var searchQuery by remember { mutableStateOf("") }
    var filteredMovies by remember { mutableStateOf<List<Result>?>(null) }

    // Update filtered movies when search query changes
    LaunchedEffect(searchQuery, moviesViewModel.getMoviesList()) {
        val allMovies = moviesViewModel.getMoviesList()
        filteredMovies = if (searchQuery.isBlank()) {
            allMovies
        } else {
            moviesViewModel.searchMoviesByTitle(searchQuery)
        }
    }

    ScaffoldCustom(
        customBody = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextCustom(
                    text = "Now Playing Movies",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 24.dp, top = 32.dp, bottom = 24.dp)
                )
                CollectApiState(
                    stateFlow = moviesViewModel.moviesState,
                    onSuccess = { movieContent ->
                        val movieList = filteredMovies ?: movieContent.results
                        if (!movieList.isNullOrEmpty()) {
                            CustomSearchBar(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = "Search movies...",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, )
                            )
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(240.dp),
                                contentPadding = PaddingValues(40.dp),
                                horizontalArrangement = Arrangement.spacedBy(40.dp),
                                verticalArrangement = Arrangement.spacedBy(40.dp)
                            ) {
                                items(movieList) { movie ->
                                    Surface(
                                        color = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(400.dp)
                                            .clickable(enabled = movie.id != null) {
                                                val path = "${NavigationItem.MovieDetail.route}/${movie.id}"
                                                navController.navigate(route = path)
                                            }
                                    ) {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            ImageCard(
                                                imgURL = if (!movie.posterPath.isNullOrBlank()) Constants.POSTER_IMAGE_BASE_URL + movie.posterPath else "https://via.placeholder.com/240x360?text=No+Image",
                                                contentDescription = "Poster for ${movie.title ?: "Movie"}",
                                            )
                                            // Overlay gradient and title
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .align(Alignment.BottomCenter)
                                                    .height(70.dp)

                                            ) {
                                                TextCustom(
                                                    text = movie.title ?: "Untitled",
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier
                                                        .align(Alignment.BottomStart)
                                                        .padding(12.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        },
        isLoading = moviesViewModel.moviesApiState.isLoading,
        hasError = moviesViewModel.moviesApiState.error
    )
} 