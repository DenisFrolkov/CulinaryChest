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
import com.den.culinarychest.presentation.common.RecipeItem
import com.den.culinarychest.presentation.common.SearchBar
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun SearchScreen() {
    Search()
}

@Composable
fun Search(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
    ) {
        Column( ){
            SearchBar()
            LazyColumn {
                items(10) {index ->
                    RecipeItem()
                }
            }

        }
    }
}