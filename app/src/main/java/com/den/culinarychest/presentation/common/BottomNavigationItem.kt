package com.den.culinarychest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationItem(
    isSelected: Boolean,
    selectedIcon: Painter,
    navBottomBarController: NavController,
    buttonNavigation: String,
    onButtonSelected: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                onButtonSelected()
                navBottomBarController.navigate(buttonNavigation)
            },
            modifier = Modifier.height(height = 50.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(size = if (isSelected) 40.dp else 30.dp)
            ) {
                Image(
                    painter = selectedIcon,
                    contentDescription = "Иконка поиска",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}