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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.RecipeItem
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe

@Composable
fun FavoriteScreen(
    controller: NavController,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    applicationUserViewModel: ApplicationUserViewModel,
    recipeViewModel: RecipeViewModel
) {
    val favoriteRecipeList =
        applicationUserFavoriteRecipeViewModel.userFavoriteRecipes.collectAsState().value

    applicationUserViewModel.token.observeForever { token ->
        favoriteRecipeList.forEach {favoriteRecipe ->
            token?.let { recipeViewModel.getRecipeById(it, "${favoriteRecipe.recipeId}") }
        }
    }
    val recipe = recipeViewModel.recipe.collectAsState().value

    if (favoriteRecipeList.isEmpty()) {
        EmptyScreenText()
    } else {
        ListRecipes(recipe, controller)
    }
}

@Composable
private fun ListRecipes(
    recipe: List<Recipe>,
    controller: NavController
) {
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
        items(recipe) { recipe ->
            RecipeItem(
                controller = controller,
                textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                recipe = recipe
            )
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