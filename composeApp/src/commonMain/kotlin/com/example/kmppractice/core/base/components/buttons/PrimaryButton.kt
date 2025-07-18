package com.example.kmppractice.core.base.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.text.TextCustom


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    isEnable : Boolean = true,
    modifier: Modifier = Modifier
        .width(300.dp)
        .height(54.dp)
) {

    val focusManager = LocalFocusManager.current

    Button(
        enabled= isEnable,
        modifier = modifier,
        onClick = {
            focusManager.clearFocus()
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    ) {
        TextCustom(
            text =text,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

