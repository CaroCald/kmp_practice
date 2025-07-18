package com.example.kmppractice.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.movie_title
import com.example.kmppractice.generated.resources.my_profile

import org.jetbrains.compose.resources.stringResource


data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    @Composable
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = stringResource(resource = Res.string.my_profile),
                icon = Icons.Filled.Person,
                route = NavigationItem.Profile.route
            ),
            BottomNavigationItem(
                label = stringResource(resource =Res.string.movie_title),
                icon = Icons.Filled.Movie,
                route = NavigationItem.MovieList.route
            ),
        )
    }
}