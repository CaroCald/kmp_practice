package com.example.kmppractice.core.base.components.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ImageCard(
    imgURL: String,
    contentDescription: String = "Movie poster"
) {
    Card(
        shape = RoundedCornerShape(20),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
    ) {
        AsyncImage(
            alignment = Alignment.Center,
            model = imgURL,
            contentDescription = contentDescription
        )
    }
} 