package com.example.kmppractice

//import androidx.compose.ui.window.ComposeUIViewController
//
//fun MainViewController() = ComposeUIViewController { App() }

import androidx.compose.ui.window.ComposeUIViewController
import com.example.kmppractice.core.di.AppModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }
fun initKoin(){
    startKoin {
        modules(AppModule())
    }.koin
}