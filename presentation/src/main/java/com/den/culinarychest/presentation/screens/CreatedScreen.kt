package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.Item.FAB
import com.den.culinarychest.presentation.common.Item.RecipeItem
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.step.Step

@Composable
fun CreatedScreen(
    controller: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 66.dp)
        ) {
            FAB(navController = controller)
        }
        Column(
            modifier = Modifier.padding(top = 82.dp).padding(horizontal = 16.dp)
        ) {
            RecipeItem(
                controller = controller,
                textRouteNavigation = AppNavigationRoute.FetchUserRecipeScreen.route,
                recipe = Recipe(recipeId = "1", id = "12", title = "12", recipeImage = "12", ingredients = "12", savedCount = 1, creationDate = "12", preparationTime = "12",
                    steps = listOf(Step(stepId = 1, description = "12", 1, recipeId = 1))
                )
            )
        }
    }
}