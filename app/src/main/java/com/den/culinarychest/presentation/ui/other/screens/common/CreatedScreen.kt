package com.den.culinarychest.presentation.ui.other.screens.common

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.other.common.components.Item.RecipeItem
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.example.culinarychest.domain.model.recipe.Recipe

@Composable
fun CreatedScreen(
    controller: NavController,
    createdViewModel: CreatedViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
) {

    val listRecipeCreatedUser =
        createdViewModel.listRecipesUser.collectAsState().value


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        ListRecipeCreatedUser(
            controller,
            createdViewModel,
            listRecipeCreatedUser,
            recipeDetailsViewModel
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
    createdViewModel: CreatedViewModel,
    createdRecipeList: List<Recipe>,
    recipeDetailsViewModel: RecipeDetailsViewModel
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
        if (createdRecipeList.isEmpty()) {
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
                            .padding(start = 10.dp, top = 10.dp)
                    )
                }
            }
        } else {
            items(createdRecipeList) { recipe ->
                RecipeItem(
                    controller = controller,
                    textRouteNavigation = AppNavigationRoute.FetchUserRecipeScreen.route,
                    recipe = recipe,
                    recipeDetailsViewModel = recipeDetailsViewModel
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun FAB(
    navController: NavController

) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(size = 25.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(size = 25.dp))
            .size(size = 50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(AppNavigationRoute.CreatingRecipeScreen.route)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
        )
    }
}