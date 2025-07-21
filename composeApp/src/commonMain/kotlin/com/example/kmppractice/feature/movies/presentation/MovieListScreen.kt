package com.example.kmppractice.feature.movies.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.kmppractice.core.base.components.bottomBar.BottomNavigationBar
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.shared.ImageCard
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.navigation.NavigationItem
import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.core.utils.CollectApiState
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MovieScreen(
    navController: Navigator,
    moviesViewModel: MoviesViewModel
) {
    ScaffoldCustom(
        customBottomBar = { BottomNavigationBar(navController) },
        customBody = {
            CollectApiState(
                stateFlow = moviesViewModel.moviesState,
                onSuccess = { movieContent ->
                    val movieList = movieContent.results
                    if (!movieList.isNullOrEmpty()) {
                        Column {
                            // Hero Image
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            ) {
                                AsyncImage(
                                    model = Constants.POSTER_IMAGE_BASE_URL + movieList.first().posterPath,
                                    contentDescription = "Featured movie poster",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .wrapContentWidth(),
                                    onError = { error ->
                                        println("Image loading failed: ${error.result.throwable}")
                                    }
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
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClicked() },
        horizontalAlignment = Alignment.Start
    ) {
        ImageCard(
            imgURL = Constants.POSTER_IMAGE_BASE_URL + image,
            contentDescription = "Poster for $title"
        )
        TextCustom(text = title)
    }
}