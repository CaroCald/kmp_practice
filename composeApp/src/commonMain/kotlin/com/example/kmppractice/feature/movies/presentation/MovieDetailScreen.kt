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
import com.example.kmppractice.core.base.api_generics.GenericApiState
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.shared.ImageCard
import com.example.kmppractice.core.base.components.text.TextCustom
import com.example.kmppractice.core.base.components.toolbar.ToolBarCustom
import com.example.kmppractice.core.utils.Constants
import com.example.kmppractice.core.utils.collectApiState
import com.example.kmppractice.feature.movies.data.MovieDetailContent
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.title_detail
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieDetailScreen(
    navHostController: Navigator,
    id: String,
    moviesViewModel: MoviesViewModel
) {
    LaunchedEffect(id) {
        moviesViewModel.getMovieDetail(id)
    }

    ScaffoldCustom(
        customToolBar = { 
            ToolBarCustom(
                navController = navHostController,
                title = stringResource(Res.string.title_detail),
                hasBackButton = true, 
                onClick = {
                    moviesViewModel.clearMovieDetail()
                }
            ) 
        },
        customBody = {
            collectApiState(
                stateFlow = moviesViewModel.movieDetailState,
                onSuccess = { movieDetail ->
                    MovieDetailContent(movieDetail = movieDetail)
                },
                onError = { exception ->
                    // Error state is handled by ScaffoldCustom
                },
                onLoading = {
                    // Loading state is handled by ScaffoldCustom
                }
            )
        },
        onClickError = {
            // Just clear the error state, don't refresh
            moviesViewModel.clearMovieDetailError()
        },
        isLoading = moviesViewModel.movieDetailApiState.isLoading,
        hasError = moviesViewModel.movieDetailApiState.error
    )
}

@Composable
private fun MovieDetailContent(movieDetail: MovieDetailContent) {
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
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Poster Image
        Box(modifier = Modifier.height(350.dp)) {
            ImageCard(
                imgURL = Constants.POSTER_IMAGE_BASE_URL + movieDetail.posterPath,
                contentDescription = "Poster for ${movieDetail.title}"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Overview
        if (movieDetail.overview.isNotBlank()) {
            TextCustom(
                text = movieDetail.overview,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Movie Details
        MovieDetailsSection(movieDetail = movieDetail)
        
        // Additional Info
        if (movieDetail.genres.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            GenresSection(genres = movieDetail.genres)
        }
    }
}

@Composable
private fun MovieDetailsSection(movieDetail: MovieDetailContent) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DetailItem(
            label = "Release Date",
            value = movieDetail.releaseDate
        )
        
        DetailItem(
            label = "Status", 
            value = movieDetail.status
        )
        
        if (movieDetail.runtime > 0) {
            DetailItem(
                label = "Runtime",
                value = "${movieDetail.runtime} min"
            )
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextCustom(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        TextCustom(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun GenresSection(genres: List<com.example.kmppractice.feature.movies.data.Genre>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextCustom(
            text = "Genres",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TextCustom(
            text = genres.joinToString(", ") { it.name },
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}
