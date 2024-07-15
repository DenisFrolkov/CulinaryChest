package com.den.culinarychest.presentation.other.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.other.common.Item.RecipeItem
import com.den.culinarychest.presentation.other.route.AppNavigationRoute
import com.den.culinarychest.presentation.other.ui.theme.SoftGray
import com.den.culinarychest.presentation.other.ui.theme.SoftOrange
import com.den.culinarychest.presentation.other.ui.theme.SoftPink
import com.den.culinarychest.presentation.main.viewmodel.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.recipe.Recipe

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteScreen(
    controller: NavController,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    recipeViewModel: RecipeViewModel,
    tokenManager: TokenManager
) {

    val favoriteRecipeList = applicationUserFavoriteRecipeViewModel.userFavoriteRecipes.collectAsState().value
    tokenManager.getToken()?.let { token ->
        val recipeIds = favoriteRecipeList.map { it.recipeId.toString() }
        recipeViewModel.getRecipesByIds(token, recipeIds)
    }

    val recipe =
        recipeViewModel.recipesById.collectAsState().value


    if (recipe.isEmpty()) {
        EmptyScreenText()
    } else {
        ListRecipes(controller, recipe, recipeViewModel, applicationUserFavoriteRecipeViewModel, applicationUserRecipeViewModel, tokenManager)
    }
}

@Composable
private fun ListRecipes(
    controller: NavController,
    recipeDtoList: List<Recipe>,
    recipeViewModel: RecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    tokenManager: TokenManager
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

        if (recipeDtoList != null) {
            items(recipeDtoList) { recipe ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                    recipe = recipe,
                    tokenManager = tokenManager,
                    applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
                    recipeViewModel = recipeViewModel
                )
            }
        } else {
            item {
                CircularProgressIndicator(
                    color = SoftOrange,
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
            text = stringResource(R.string.no_saved_recipes_text_text),
            style = TextStyle(
                color = SoftGray,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp, top = 82.dp)
        )
    }
}