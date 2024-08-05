package com.den.culinarychest.presentation.ui.other.screens.common

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
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.other.common.components.Item.RecipeItem
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.example.culinarychest.domain.model.recipe.Recipe

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FavoriteScreen(
    controller: NavController,
    favoriteViewModel: FavoriteViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
) {

    val favoriteRecipeList = favoriteViewModel.listFavoriteRecipesUser.collectAsState().value
        val recipeIds = favoriteRecipeList.map { it.recipeId.toString() }
        favoriteViewModel.getListRecipesByIds(recipeIds)

    val recipe =
        favoriteViewModel.listRecipesById.collectAsState().value


    if (recipe.isEmpty()) {
        EmptyScreenText()
    } else {
        ListRecipes(controller, recipe, recipeDetailsViewModel)
    }
}

@Composable
private fun ListRecipes(
    controller: NavController,
    recipeList: List<Recipe>,
    recipeDetailsViewModel: RecipeDetailsViewModel,
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

        if (recipeList.isEmpty()) {
            item {
                CircularProgressIndicator(
                    color = SoftOrange,
                )
            }
        } else {
            items(recipeList) { recipe ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                    recipe = recipe,
                    recipeDetailsViewModel = recipeDetailsViewModel
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