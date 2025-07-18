package com.example.kmppractice.core.base.components.text


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun TextCustom(
    text: String,
    modifier: Modifier? = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight? = null,
    color: Color =  MaterialTheme.colorScheme.secondary,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier ?: Modifier,
        text =  text,
        style = style ,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign
    )
}

