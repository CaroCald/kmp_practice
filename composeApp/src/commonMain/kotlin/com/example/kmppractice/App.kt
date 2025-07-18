package com.example.kmppractice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kmppractice.core.navigation.Navigation
import com.example.kmppractice.core.theme.PracticeComposeTheme
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {
    PreComposeApp{
        PracticeComposeTheme {
            MyApp(modifier = Modifier.fillMaxSize())
        }
    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        Navigation(navigator = rememberNavigator())
    }
}
