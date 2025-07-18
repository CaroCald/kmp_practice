package com.example.kmppractice.core.base.components.alerts

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.confirm
import com.example.kmppractice.generated.resources.warning_title
import com.example.kmppractice.core.base.components.text.TextCustom
import org.jetbrains.compose.resources.stringResource


@Composable
fun CustomAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    title: @Composable () -> Unit = {
        TextCustom(
            text = stringResource(resource = Res.string.warning_title),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    },
    message: String,
    onClick: () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
                               },
            title = title,
            text = {
                TextCustom(
                    text = message,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(onClick = {
                    onClick()
                    shouldShowDialog.value = false

                }) {
                    TextCustom(
                        text = stringResource(resource = Res.string.confirm),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            },
            dismissButton = dismissButton
        )
    }
}
