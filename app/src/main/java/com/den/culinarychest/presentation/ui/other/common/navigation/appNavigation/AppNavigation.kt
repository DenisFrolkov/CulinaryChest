//@file:Suppress("DEPRECATION")
package com.den.culinarychest.presentation.ui.other.common.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.den.culinarychest.presentation.ui.main.viewmodel.ImageViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.AuthorizationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.RegistrationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.common.TokenViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.profile.ProfileViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatingRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageOtherRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageRecipeUserViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageStepsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.SearchViewModel
import com.den.culinarychest.presentation.ui.other.common.navigation.bottomNavigation.BottomNavigationBar
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.other.screens.auth.AuthorizationScreen
import com.den.culinarychest.presentation.ui.other.screens.auth.RegistrationScreen
import com.den.culinarychest.presentation.ui.other.screens.common.CreatingRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.EditRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.FetchOtherUserRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.FetchUserRecipeScreen


@Composable
fun AppNavigation(
    imageViewModel: ImageViewModel,
    tokenViewModel: TokenViewModel,
    registrationViewModel: RegistrationViewModel,
    authorizationViewModel: AuthorizationViewModel,
    searchViewModel: SearchViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    createdViewModel: CreatedViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    creatingRecipeViewModel: CreatingRecipeViewModel,
    manageRecipeUserViewModel: ManageRecipeUserViewModel,
    manageOtherRecipeViewModel: ManageOtherRecipeViewModel,
    manageStepsViewModel: ManageStepsViewModel,
) {

    val token by tokenViewModel.token.collectAsState()

    val isUserAuthorized = token == null

    val startDestination = if (isUserAuthorized) {
        AppNavigationRoute.AuthorizationScreen.route

    } else {
        AppNavigationRoute.BottomAppNavigationBar.route
    }

    val appNavigationController = rememberNavController()
    NavHost(
        navController = appNavigationController, startDestination = startDestination
    ) {
        composable(AppNavigationRoute.AuthorizationScreen.route) {
            AuthorizationScreen(
                navController = appNavigationController,
                authorizationViewModel = authorizationViewModel,
                tokenViewModel = tokenViewModel,
            )

        }
        composable(AppNavigationRoute.RegistrationScreen.route) {
            RegistrationScreen(
                navController = appNavigationController,
                registrationApplicationUser = registrationViewModel,
                authorizationViewModel = authorizationViewModel,
                tokenViewModel = tokenViewModel
            )
        }
        composable(AppNavigationRoute.BottomAppNavigationBar.route) {
            BottomNavigationBar(
                navController = appNavigationController,
                imageViewModel = imageViewModel,
                searchViewModel = searchViewModel,
                recipeDetailsViewModel = recipeDetailsViewModel,
                createdViewModel = createdViewModel,
                favoriteViewModel = favoriteViewModel,
                profileViewModel = profileViewModel,
                tokenViewModel = tokenViewModel,
            )
        }
        composable(
            AppNavigationRoute.FetchOtherUserRecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
                ?: "Надо придумать реализацию, если такого рецепта не существует"
            recipeId.let { recipe ->
                recipeDetailsViewModel.getRecipeById(
                    recipe
                )
                recipeDetailsViewModel.getFavoriteRecipeByRecipeId(recipe)
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState(initial = emptyList())
            recipeInfo.value.forEach { recipe ->
                imageViewModel.fetchRecipePhoto(recipe.imageUrl)
                FetchOtherUserRecipeScreen(
                    navController = appNavigationController,
                    imageViewModel = imageViewModel,
                    recipe = recipe,
                    recipeDetailsViewModel = recipeDetailsViewModel,
                    manageOtherRecipeViewModel = manageOtherRecipeViewModel,
                )
            }
        }
        composable(
            AppNavigationRoute.FetchUserRecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
                ?: "Надо придумать реализацию, если такого рецепта не существует"

            recipeId.let { recipe ->
                recipeDetailsViewModel.getRecipeById(
                    recipe
                )
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                imageViewModel.fetchRecipePhoto(recipe.imageUrl)
                FetchUserRecipeScreen(
                    navController = appNavigationController,
                    imageViewModel = imageViewModel,
                    manageRecipeUserViewModel = manageRecipeUserViewModel,
                    recipe = recipe,
                )
            }
        }
        composable(
            AppNavigationRoute.EditRecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
                ?: "Надо придумать реализацию, если такого рецепта не существует"

            recipeId.let { recipe ->
                recipeDetailsViewModel.getRecipeById(
                    recipe
                )
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                imageViewModel.fetchRecipePhoto(recipe.imageUrl)
                EditRecipeScreen(
                    navController = appNavigationController,
                    imageViewModel = imageViewModel,
                    manageRecipeUserViewModel = manageRecipeUserViewModel,
                    manageStepsViewModel = manageStepsViewModel,
                    recipe = recipe,
                )
            }
        }
        composable(AppNavigationRoute.CreatingRecipeScreen.route) {
            CreatingRecipeScreen(
                navController = appNavigationController,
                creatingRecipeViewModel = creatingRecipeViewModel,
            )
        }
    }
}

