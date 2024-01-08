package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.RecipeItem
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun SearchScreen(
    navController: NavController
) {
    Search()
}

@Composable
fun Search(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
    ) {
        RecipeItem()
    }
}