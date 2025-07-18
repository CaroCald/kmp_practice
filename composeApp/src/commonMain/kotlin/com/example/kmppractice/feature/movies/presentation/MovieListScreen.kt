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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.kmppractice.core.base.components.bottomBar.BottomNavigationBar
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.navigation.NavigationItem
import com.example.kmppractice.core.utils.Constants
import moe.tlaster.precompose.navigation.Navigator


@Composable
fun MovieScreen(
    navController: Navigator,
    moviesViewModel: MoviesViewModel
) {

    ScaffoldCustom(
        customBottomBar = { BottomNavigationBar(navController) },
        customBody = {
            Box {
                moviesViewModel.EventApi(onSuccess = {
                    val movieList = moviesViewModel.getResultSMovies()
                    if (movieList != null) {

                        Column {
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .fillMaxWidth()  // Ensures Box fills screen width
                                    .height(300.dp)
                            ) {
                                AsyncImage(
                                    // model = "https://image.tmdb.org/t/p/w500/1E5baAaEse26fej7uHcjOgEE2t2.jpg",
                                    model = Constants.POSTER_IMAGE_BASE_URL + movieList.first().posterPath,
                                    contentDescription = "Translated description of what the image contains",
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .wrapContentWidth(),// Center the image horizontally
                                    onError = { error ->
                                        // Log the error or display a message
                                        println("Image loading failed: ${error.result.throwable}")
                                    },

                                    )
                            }

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                items(movieList) { items ->
                                    MovieCard(
                                        title = items.title!!,
                                        image = items.posterPath!!,
                                        onClicked = {
                                            val path =
                                                "${NavigationItem.MovieDetail.route}/${items.id}"
                                            navController.navigate(route = path)

                                        })
                                }
                            }
                        }
                    }

                }, onError = {

                })
            }
        },
        onClickError = {
            moviesViewModel.restoreState()
        },
        isLoading = moviesViewModel.apiState.isLoading,
        hasError = moviesViewModel.apiState.error
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

        ImageCard(imgURL = Constants.POSTER_IMAGE_BASE_URL + image)
        TextCustom(text = title)
    }
}


@Composable
fun ImageCard(imgURL: String) {
    Card(
        shape = RoundedCornerShape(20),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
    ) {
        AsyncImage(
            alignment = Alignment.Center,
            model = imgURL,
            contentDescription = "Translated description of what the image contains"
        )
    }
}