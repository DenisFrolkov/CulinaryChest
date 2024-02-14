package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.Item.RecipeItem
import com.den.culinarychest.presentation.common.Item.SearchBarItem
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun SearchScreen(
    navController: NavController
) {
    Search(navController = navController)
}

@Composable
fun Search(
    navController: NavController
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
    ) {
        Column(){
            SearchBarItem()
            LazyColumn(
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                items(10) {index ->
                    RecipeItem(navController = navController)
                }
            }
        }
    }
}