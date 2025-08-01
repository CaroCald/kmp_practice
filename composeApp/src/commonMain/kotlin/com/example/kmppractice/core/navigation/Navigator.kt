package com.example.kmppractice.core.navigation

import androidx.compose.runtime.Composable
import com.example.kmppractice.feature.movies.presentation.MovieDetailScreen
import com.example.kmppractice.feature.movies.presentation.MovieScreen
import com.example.kmppractice.feature.movies.presentation.MoviesViewModel
import com.example.kmppractice.feature.profile.presentation.ProfileScreen
import com.example.kmppractice.core.navigation.getMovieListScreenForPlatform
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path


@Composable
fun Navigation(navigator: Navigator) {
    val viewModel = koinViewModel(MoviesViewModel::class){ org.koin.core.parameter.parametersOf()}

    NavHost(
        navigator = navigator,
        initialRoute = NavigationItem.MovieList.route
    ) {
        scene(route = NavigationItem.Splash.route) {
        }

        scene( route = NavigationItem.Home.route){

        }

        scene( route = NavigationItem.Login.route){

        }

        scene( route = NavigationItem.Profile.route){
            ProfileScreen(navigator)
        }

        scene( route = NavigationItem.MovieList.route){
            getMovieListScreenForPlatform(navigator, viewModel).invoke()
        }

        scene( route = NavigationItem.MovieDetail.route+"/{id}"){
            val idFromPath = it.path<String>("id")
            idFromPath?.let { id ->
                MovieDetailScreen(navigator, id, viewModel)
            }

        }
    }
}