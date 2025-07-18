package com.example.kmppractice.core.base.components.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.text.TextCustom

@Composable
fun PrimaryInput(
    text: String,
    onTextChange: (String) -> Unit,
    title: String,
    isPassword : Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = null,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
   // val context = LocalContext.current
    var showPassword by remember { mutableStateOf(value = false) }

    val textFieldIsFocused = interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        interactionSource = interactionSource,
        value = text,
        isError = (isError && textFieldIsFocused.value),
        onValueChange = { onTextChange(it) },
        label = { TextCustom(title) },
        supportingText = {
            if (isError && textFieldIsFocused.value) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.secondary,
            unfocusedTextColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor =  Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
        ),
        visualTransformation = if (showPassword || !isPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if(isPassword){
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "hide_password"
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPassword = true }) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "hide_password"
                        )
                    }
                }
            }
          },
        keyboardActions = KeyboardActions (
            onDone = { focusManager.clearFocus() }
        ),
        keyboardOptions = if (isPassword)  KeyboardOptions(keyboardType = KeyboardType.Password) else keyboardOptions
    )
}