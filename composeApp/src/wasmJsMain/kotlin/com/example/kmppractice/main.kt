package com.example.kmppractice

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.example.kmppractice.core.di.AppModule
import kotlinx.browser.document
import org.koin.core.context.GlobalContext.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    startKoin {
        modules(AppModule())
    }
    ComposeViewport(document.body!!) {
        App()
    }
}