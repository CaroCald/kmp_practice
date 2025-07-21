package com.example.kmppractice.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kmppractice.core.base.components.bottomBar.BottomNavigationBar
import com.example.kmppractice.core.base.components.buttons.PrimaryButton
import com.example.kmppractice.core.base.components.scaffold.ScaffoldCustom
import com.example.kmppractice.core.base.components.text.TextCustom
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun ProfileScreen(
    navController: Navigator,
) {
    ScaffoldCustom(
        customBottomBar = { BottomNavigationBar(navController) },
        customBody = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Avatar
                Surface(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        TextCustom(
                            text = "JD",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Name
                TextCustom(
                    text = "Jane Doe",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Email
                TextCustom(
                    text = "emalil@dkdk.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Edit Button
                PrimaryButton(
                    onClick = {

                    },
                    text = "Edit Profile"
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Logout Button
                PrimaryButton(
                    onClick = {},
                    text = "Logo ut "
                )

            }
        },
        isLoading = false
    )
} 