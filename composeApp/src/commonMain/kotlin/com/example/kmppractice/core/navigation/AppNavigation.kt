package com.example.kmppractice.core.navigation
import androidx.compose.runtime.Composable
import com.example.kmppractice.feature.movies.presentation.MovieScreen
import com.example.kmppractice.feature.movies.presentation.MovieWebScreen
import com.example.kmppractice.feature.movies.presentation.MoviesViewModel
import com.example.kmppractice.core.utils.PlatformUtils
import moe.tlaster.precompose.navigation.Navigator
enum class Screen {

    HOME,
    LOGIN,
    SPLASH,
    MOVIE_LIST,
    PROFILE,
    MOVIE_DETAIL,
    WELCOME
}
sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object Login : NavigationItem(Screen.LOGIN.name)
    data object Splash : NavigationItem(Screen.SPLASH.name)
    data object MovieList : NavigationItem(Screen.MOVIE_LIST.name)
    data object Profile : NavigationItem(Screen.PROFILE.name)
    data object MovieDetail : NavigationItem(Screen.MOVIE_DETAIL.name)
    data object Welcome : NavigationItem(Screen.WELCOME.name)
}

fun getMovieListScreenForPlatform(navController: Navigator, moviesViewModel: MoviesViewModel): @Composable () -> Unit {
    return when {
        PlatformUtils.isWeb() -> { { MovieWebScreen(navController, moviesViewModel) } }
        PlatformUtils.isAndroid() || PlatformUtils.isIOS() -> { { MovieScreen(navController, moviesViewModel) } }
        else -> { { MovieScreen(navController, moviesViewModel) } } // fallback
    }
}