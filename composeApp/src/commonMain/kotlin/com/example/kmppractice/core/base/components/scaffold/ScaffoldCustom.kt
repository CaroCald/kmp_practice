package com.example.kmppractice.core.base.components.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.kmppractice.core.base.api_generics.CustomError
import com.example.kmppractice.core.base.components.alerts.CustomAlertDialog
import com.example.kmppractice.generated.resources.Res
import com.example.kmppractice.generated.resources.error_generic
import com.example.kmppractice.core.base.components.loading.Loading
import org.jetbrains.compose.resources.stringResource


@Composable
fun ScaffoldCustom(
    customToolBar: (@Composable () -> Unit)? = null,
    customBottomBar: (@Composable () -> Unit)? = null,
    customFloatingButton: (@Composable () -> Unit)? = null,
    customBody: @Composable () -> Unit ,
    isLoading: Boolean,
    hasError : Any? = null,
    onClickError: () -> Unit = {}
) {
    Box {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar =  customToolBar ?: {},
            bottomBar = customBottomBar ?: {},
            floatingActionButton =  customFloatingButton ?: {}
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                customBody()
            }
        }
        if (isLoading) {
            Loading()
        }
        if (hasError != null) {
            val shouldShowDialog = remember { mutableStateOf(true) }
            val errorMessage = when (hasError) {
                is CustomError -> hasError.message ?: stringResource(resource = Res.string.error_generic)
                is Throwable -> hasError.message ?: stringResource(resource = Res.string.error_generic)
                else -> stringResource(resource = Res.string.error_generic)
            }
            
            CustomAlertDialog(
                shouldShowDialog = shouldShowDialog,
                onClick = {
                    shouldShowDialog.value = false
                    onClickError()
                },
                message = errorMessage
            )
        }
    }
}