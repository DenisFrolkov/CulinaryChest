package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.FavoriteRecipeItem
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.TokenManager
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.recipe.Recipe

@Composable
fun FavoriteScreen(
    controller: NavController,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    recipeViewModel: RecipeViewModel,
    tokenManager: TokenManager
) {
    val favoriteRecipeList =
        applicationUserFavoriteRecipeViewModel.userFavoriteRecipes.collectAsState().value

    if (favoriteRecipeList.isEmpty()) {
        EmptyScreenText()
    } else {
        ListRecipes(controller, favoriteRecipeList, recipeViewModel, tokenManager)
    }
}

@Composable
private fun ListRecipes(
    controller: NavController,
    favoriteRecipeList: List<FavoriteRecipe>,
    recipeViewModel: RecipeViewModel,
    tokenManager: TokenManager
) {

    val recipeCache = remember { mutableMapOf<String, Recipe>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
            .padding(bottom = 40.dp)
            .padding(horizontal = 8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(72.dp))
        }

        items(favoriteRecipeList) { favoriteRecipe ->
            val token = tokenManager.getToken()

            token?.let { recipeViewModel.getRecipeById(it, "${favoriteRecipe.recipeId}") }

            val recipeList = recipeViewModel.recipe.collectAsState().value

            recipeList.forEach { recipe ->
                FavoriteRecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                    recipe = recipe,
                    recipeViewModel = recipeViewModel,
                    tokenManager = tokenManager
                )
            }
        }

    }
}

@Composable
private fun EmptyScreenText() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        Text(
            text = stringResource(R.string.empty_text),
            style = TextStyle(
                color = SoftGray,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp, top = 82.dp)
        )
    }
}