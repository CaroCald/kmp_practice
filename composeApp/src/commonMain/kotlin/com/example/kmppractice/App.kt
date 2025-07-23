package com.example.kmppractice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kmppractice.core.navigation.Navigation
import com.example.kmppractice.core.theme.PracticeComposeTheme
import com.example.kmppractice.feature.weather_current.WeatherScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PreComposeApp{
        PracticeComposeTheme {
            WeatherScreen()
        }
    }
}

