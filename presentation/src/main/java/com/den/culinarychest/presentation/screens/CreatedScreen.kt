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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.FAB
import com.den.culinarychest.presentation.common.Item.RecipeItem
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.recipe.Recipe

@Composable
fun CreatedScreen(
    controller: NavController,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    recipeViewModel: RecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager
) {

    tokenManager.getToken()?.let {
        applicationUserRecipeViewModel.getApplicationUserRecipes(it)
    }

    val listRecipeCreatedUser =
        applicationUserRecipeViewModel.applicationUserRecipes.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        ListRecipeCreatedUser(
            controller,
            listRecipeCreatedUser,
            tokenManager = tokenManager,
            applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
            recipeViewModel = recipeViewModel
        )
        FABButton(controller)
    }
}

@Composable
private fun FABButton(controller: NavController) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, bottom = 66.dp)
    ) {
        FAB(
            navController = controller
        )
    }
}

@Composable
private fun ListRecipeCreatedUser(
    controller: NavController,
    listRecipeCreatedUser: List<Recipe>,
    tokenManager: TokenManager,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    recipeViewModel: RecipeViewModel
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
        if (listRecipeCreatedUser == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = SoftPink)
                ) {
                    Text(
                        text = stringResource(R.string.no_created_recipes_text),
                        style = TextStyle(
                            color = SoftGray,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp, top = 82.dp)
                    )
                }
            }
        } else {
            items(listRecipeCreatedUser) { recipeCreatedUser ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchUserRecipeScreen.route,
                    recipe = recipeCreatedUser,
                    tokenManager = tokenManager,
                    applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
                    recipeViewModel = recipeViewModel
                )
            }
        }
    }
}