package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.Item.RecipeItem
import com.den.culinarychest.presentation.common.Item.SearchBarItem
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.TokenManager

@Composable
fun SearchScreen(
    navController: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    recipeViewModel: RecipeViewModel,
    tokenManager: TokenManager
) {
    Search(
        controller = navController,
        recipeViewModel = recipeViewModel
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(
    controller: NavController,
    recipeViewModel: RecipeViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val recipeList = recipeViewModel.recipes.collectAsState().value

    Scaffold(
        topBar = {
            SearchBarItem { searchText = it }
        },
        modifier = Modifier
            .background(SoftPink)
            .padding(horizontal = 8.dp, vertical = 10.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(SoftPink)
                .padding(bottom = 40.dp)
                .padding(horizontal = 8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(62.dp))
            }
            items(recipeList) { recipe ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                    recipe = recipe
                )
            }
        }
    }
}