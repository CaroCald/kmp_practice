package com.example.kmppractice.feature.movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.base.components.toolbar.ToolBarCustom
import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.title_detail
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieDetailScreen(navHostController: Navigator,
                      id:String,
                      moviesVieModel: MoviesViewModel) {

    LaunchedEffect(Unit){
        println("Fetching DETAILS")
       moviesVieModel.getMovieDetail(id)
    }

    ScaffoldCustom(
        customToolBar = { ToolBarCustom(navController = navHostController,
            title = stringResource(Res.string.title_detail),
            hasBackButton = true, onClick = {
                moviesVieModel.restoreStateDetail()
            }) },
        customBody = {
            moviesVieModel.EventApiDetail(
                onSuccess = {
                val movieDetail = moviesVieModel.getDetailMovies()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    TextCustom(
                        text = movieDetail.title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    // Poster Image
                    Box(modifier = Modifier.height(350.dp)) {
                        ImageCard(
                            imgURL = Constants.POSTER_IMAGE_BASE_URL + movieDetail.posterPath,

                            )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Overview
                    TextCustom(
                        text = movieDetail.overview,
                        textAlign = TextAlign.Justify,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Details (Release Date and Status)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextCustom(
                                text = "Release Date",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            TextCustom(text = movieDetail.releaseDate)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextCustom(
                                text = "Status",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                            TextCustom(text = movieDetail.status)
                        }
                    }
                }


            }, onError = {
                    //moviesVieModel.restoreStateDetail()
            })
        },
        onClickError = {
            moviesVieModel.restoreStateDetail()
        },
        isLoading = moviesVieModel.apiState.isLoading,
        hasError = moviesVieModel.apiState.error
    )
}
