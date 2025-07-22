package com.example.kmppractice.core.base.components.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.text.TextCustom

/**
 * Reusable search bar component with customizable features
 * 
 * @param value Current search query value
 * @param onValueChange Callback when search query changes
 * @param modifier Modifier for the search bar
 * @param placeholder Placeholder text for the search input
 * @param leadingIcon Leading icon (defaults to search icon)
 * @param trailingIcon Trailing icon (defaults to clear icon when text is not empty)
 * @param singleLine Whether the input should be single line
 * @param enabled Whether the search bar is enabled
 * @param onClear Callback when clear button is pressed (optional)
 */
@Composable
fun CustomSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    leadingIcon: ImageVector = Icons.Default.Search,
    trailingIcon: ImageVector? = Icons.Default.Clear,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    onClear: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            TextCustom(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "Search",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (value.isNotEmpty() && trailingIcon != null) {
                IconButton(
                    onClick = {
                        onValueChange("")
                        onClear?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "Clear search",
                        tint = Color.Gray
                    )
                }
            }
        },
        singleLine = singleLine,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
           focusedTextColor = Color.White,
            focusedLabelColor = Color.White
        )
    )
} 