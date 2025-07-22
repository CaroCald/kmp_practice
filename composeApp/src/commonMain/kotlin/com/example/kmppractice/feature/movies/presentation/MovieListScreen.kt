package com.example.kmppractice.feature.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.kmppractice.core.base.components.bottomBar.BottomNavigationBar
import com.example.kmppractice.core.base.components.input.CustomSearchBar
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.navigation.NavigationItem
import com.example.kmppractice.core.utils.CollectApiState
import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.feature.movies.data.Result
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MovieScreen(
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
        customBottomBar = { BottomNavigationBar(navController) },
        customBody = {
            CollectApiState(
                stateFlow = moviesViewModel.moviesState,
                onSuccess = { movieContent ->
                    //val movieList = movieContent.results
                    val movieList = filteredMovies ?: movieContent.results
                    if (!movieList.isNullOrEmpty()) {
                        Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        ) {
                            // Hero Image
                            // Header
                            TextCustom(
                                text = "Movies",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Search Bar
                            CustomSearchBar(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = "Search movies...",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp, )
                            )

                            // Results count
                            if (searchQuery.isNotBlank()) {
                                TextCustom(
                                    text = "${movieList.size} result${if (movieList.size != 1) "s" else ""} found",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            // Movie Grid
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(movieList) { movie ->
                                    MovieCard(
                                        title = movie.title ?: "",
                                        image = movie.posterPath ?: "",
                                        onClicked = {
                                            val path = "${NavigationItem.MovieDetail.route}/${movie.id}"
                                            navController.navigate(route = path)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        },
        onClickError = {
            // Just clear the error state, don't refresh
            moviesViewModel.clearMoviesError()
        },
        isLoading = moviesViewModel.moviesApiState.isLoading,
        hasError = moviesViewModel.moviesApiState.error
    )
}

@Composable
fun MovieCard(title: String, image: String, onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(260.dp) // Fixed height for all cards
            .clickable { onClicked() },
      //  shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = Constants.POSTER_IMAGE_BASE_URL + image,
                contentDescription = "Movie poster",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .weight(1f, fill = true) // Take up remaining space
            ) {
                TextCustom(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,

                )
            }
        }
    }
}