package com.shivayogi.epicnews.presentation.wigets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shivayogi.epicnews.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    shouldShowBackButton: Boolean,
    onNavigationClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title, color = Color.White) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF5759D4)),
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Image(
                    painter = painterResource(
                        id = if (shouldShowBackButton) R.drawable.ic_back else R.drawable.ic_launcher_foreground
                    ),
                    contentDescription = if (shouldShowBackButton) "Back" else "Menu",
                    modifier = Modifier.size(24.dp) // Adjust size as needed
                )
            }
        }
    )

}
