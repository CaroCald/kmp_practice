package com.example.kmppractice.core.base.components.bottomBar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.navigation.Navigator
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.navigation.BottomNavigationItem
import com.example.kmppractice.core.base.components.text.TextCustom

@Composable
fun BottomNavigationBar(navController: Navigator) {

    val currentEntry by navController.currentEntry.collectAsState(null)
    val currentDestination = currentEntry?.route?.route



    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 10.dp
    ) {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
            val selected = navigationItem.route == currentDestination
            NavigationBarItem(
                selected = selected,
                label = {
                    TextCustom(
                        navigationItem.label,
                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    )
                },

                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label,
                        tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    )
                },
                colors = NavigationBarItemDefaults.colors(

                    indicatorColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    navController.navigate(navigationItem.route)
                }
            )
        }
    }


}