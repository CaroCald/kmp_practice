package com.example.kmppractice.core.base.components.toolbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.kmppractice.core.base.components.text.TextCustom
import moe.tlaster.precompose.navigation.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolBarCustom(
    title: String? = null,
    navController: Navigator,
    hasBackButton: Boolean = false
){

    TopAppBar(
        navigationIcon = {
            if(hasBackButton){
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    ),
                    onClick = {
                    navController.popBackStack()
                }) {
                    Icon(

                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                    )
                }
            }

        },

        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = Color.White,
        ),
        title = { if(title!=null) TextCustom(text = title) }
    )
}
