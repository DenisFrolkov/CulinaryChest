package com.den.culinarychest.presentation.TopNavigationBar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.den.culinarychest.presentation.common.TopBarButton
import com.den.culinarychest.presentation.screens.CreatedScreen
import com.den.culinarychest.presentation.screens.FavoriteScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TopNavigationBar() {
    val topBarNavController = rememberNavController()

    var favoriteSelected by remember { mutableStateOf(true)}
    var createdSelected by remember { mutableStateOf(false)}
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceAround
            ) {
                TopBarButton(
                    isSelected = favoriteSelected,
                    textButton = "Избранное",
                    topBarNavController = topBarNavController,
                    buttonNavigation = TopNavigationRoute.FavoriteScreen.route
                ){
                    favoriteSelected = it
                    createdSelected = !it
                }
                TopBarButton(
                    isSelected = createdSelected,
                    textButton = "Созданное",
                    topBarNavController = topBarNavController,
                    buttonNavigation = TopNavigationRoute.CreatedScreen.route
                ) {
                    createdSelected = it
                    favoriteSelected = !it
                }
            }
        }
    ) {
        NavHost(
            navController = topBarNavController,
            startDestination = TopNavigationRoute.FavoriteScreen.route
        ) {
            composable(TopNavigationRoute.FavoriteScreen.route) {
                FavoriteScreen()
            }
            composable(TopNavigationRoute.CreatedScreen.route) {
                CreatedScreen()
            }
        }

    }
}