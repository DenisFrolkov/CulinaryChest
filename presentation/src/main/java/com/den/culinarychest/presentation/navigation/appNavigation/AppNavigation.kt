package com.den.culinarychest.presentation.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.navigation.bottomNavigation.BottomNavigationBar
import com.den.culinarychest.presentation.screens.AuthorizationScreen
import com.den.culinarychest.presentation.screens.CreatingRecipeScreen
import com.den.culinarychest.presentation.screens.EditRecipeScreen
import com.den.culinarychest.presentation.screens.FetchOtherUserRecipeScreen
import com.den.culinarychest.presentation.screens.FetchUserRecipeScreen
import com.den.culinarychest.presentation.screens.RegistrationScreen
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeStepsViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.TokenManager

@Composable
fun AppNavigation(
    applicationUserViewModel: ApplicationUserViewModel,
    recipeViewModel: RecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    recipeStepsViewModel: RecipeStepsViewModel,
    tokenManager: TokenManager
) {

    val token = remember { tokenManager.getToken() }

    val isUserAuthorized = token != null

    val navController = rememberNavController()

    val startDestination = if (isUserAuthorized) {
        AppNavigationRoute.BottomAppNavigationBar.route
    } else {
        AppNavigationRoute.AuthorizationScreen.route
    }

    val appNavigationController = rememberNavController()
    NavHost(
        navController = appNavigationController,
        startDestination = startDestination
    )
    {
        composable(AppNavigationRoute.AuthorizationScreen.route) {
            AuthorizationScreen(
                navController = appNavigationController,
                applicationUserViewModel = applicationUserViewModel,
                tokenManager = tokenManager
            )

        }
        composable(AppNavigationRoute.RegistrationScreen.route) {
            RegistrationScreen(
                navController = appNavigationController,
                applicationUserViewModel = applicationUserViewModel,
                tokenManager = tokenManager
            )
        }
        composable(AppNavigationRoute.BottomAppNavigationBar.route) {

            tokenManager.getToken()?.let { recipeViewModel.getRecipes(it) }
            tokenManager.getToken()?.let { applicationUserFavoriteRecipeViewModel.getApplicationUserFavoriteRecipes(it) }

            BottomNavigationBar(
                navController = appNavigationController,
                applicationUserViewModel = applicationUserViewModel,
                recipeViewModel = recipeViewModel,
                applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
                tokenManager = tokenManager,
                applicationUserRecipeViewModel = applicationUserRecipeViewModel
            )
        }
        composable(AppNavigationRoute.FetchOtherUserRecipeScreen.route) {
            FetchOtherUserRecipeScreen(
                navController = appNavigationController,
                recipeViewModel = recipeViewModel
            )
        }
        composable(AppNavigationRoute.FetchUserRecipeScreen.route) {
            FetchUserRecipeScreen(
                navController = appNavigationController,
                applicationUserViewModel = applicationUserViewModel,
                applicationUserRecipeViewModel = applicationUserRecipeViewModel
            )
        }
        composable(AppNavigationRoute.EditRecipeScreen.route) {
            EditRecipeScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.CreatingRecipeScreen.route) {
            CreatingRecipeScreen(navController = appNavigationController)
        }
    }
}

