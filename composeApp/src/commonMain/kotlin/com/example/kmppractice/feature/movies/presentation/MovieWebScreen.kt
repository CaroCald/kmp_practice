package com.example.kmppractice.feature.movies.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.shared.ImageCard
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.utils.CollectApiState
import com.example.kmppractice.core.utils.Constants
import moe.tlaster.precompose.navigation.Navigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun MovieWebScreen(
    navController: Navigator,
    moviesViewModel: MoviesViewModel
) {
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
                        val movieList = movieContent.results
                        if (!movieList.isNullOrEmpty()) {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(240.dp),
                                contentPadding = PaddingValues(40.dp),
                                horizontalArrangement = Arrangement.spacedBy(40.dp),
                                verticalArrangement = Arrangement.spacedBy(40.dp)
                            ) {
                                items(movieList) { movie ->
                                    Surface(
                                       // shape = RoundedCornerShape(24.dp),
                                       // shadowElevation = 16.dp,
                                       // border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                                        color = MaterialTheme.colorScheme.background,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(400.dp)
                                            .clickable(enabled = movie.id != null) {
                                                movie.id?.let { id ->
                                                    navController.navigate("MovieDetail/$id")
                                                }
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