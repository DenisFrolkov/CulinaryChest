package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager

@Composable
fun SearchScreen(
    navController: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    recipeViewModel: RecipeViewModel,
    tokenManager: TokenManager
) {

    tokenManager.getToken()?.let {
        recipeViewModel.getRecipes(it, null)
        applicationUserViewModel.getApplicationUserInfo(it)
    }


    Search(
        controller = navController,
        recipeViewModel = recipeViewModel,
        applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
        tokenManager = tokenManager
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(
    controller: NavController,
    recipeViewModel: RecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager
) {
    var searchText by remember { mutableStateOf("") }

    val recipeList = recipeViewModel.listRecipes.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
            .padding(bottom = 40.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            SearchBarItem(
                recipeViewModel = recipeViewModel,
                tokenManager = tokenManager,
                onTextChanged = { text -> searchText = text }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            items(recipeList) { recipe ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                    recipe = recipe,
                    tokenManager = tokenManager,
                    applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel
                )
            }
        }

    }
}