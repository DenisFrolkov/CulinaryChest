//@file:Suppress("DEPRECATION")
package com.den.culinarychest.presentation.other.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.den.culinarychest.presentation.other.navigation.bottomNavigation.BottomNavigationBar
import com.den.culinarychest.presentation.other.route.AppNavigationRoute
import com.den.culinarychest.presentation.other.screens.AuthorizationScreen
import com.den.culinarychest.presentation.other.screens.CreatingRecipeScreen
import com.den.culinarychest.presentation.other.screens.EditRecipeScreen
import com.den.culinarychest.presentation.other.screens.FetchOtherUserRecipeScreen
import com.den.culinarychest.presentation.other.screens.FetchUserRecipeScreen
import com.den.culinarychest.presentation.other.screens.RegistrationScreen
import com.den.culinarychest.presentation.main.viewmodel.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.ApplicationUserInfoViewModel
import com.den.culinarychest.presentation.main.viewmodel.AuthorizationViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeStepsViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.RegistrationViewModel
import com.example.culinarychest.data.data.repository.TokenManager


@Composable
fun AppNavigation(
    applicationUserInfoViewModel: ApplicationUserInfoViewModel,
    registrationViewModel: RegistrationViewModel,
    authorizationViewModel: AuthorizationViewModel,
    recipeViewModel: RecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    recipeStepsViewModel: RecipeStepsViewModel,
    tokenManager: TokenManager
) {

    val token = remember { tokenManager.getToken() }

    val isUserAuthorized = token != null

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
                authorizationViewModel = authorizationViewModel,
                tokenManager = tokenManager
            )

        }
        composable(AppNavigationRoute.RegistrationScreen.route) {
            RegistrationScreen(
                navController = appNavigationController,
                registrationApplicationUser = registrationViewModel,
                authorizationViewModel = authorizationViewModel,
                tokenManager = tokenManager
            )
        }
        composable(AppNavigationRoute.BottomAppNavigationBar.route) {
            BottomNavigationBar(
                navController = appNavigationController,
                applicationUserInfoViewModel = applicationUserInfoViewModel,
                recipeViewModel = recipeViewModel,
                applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
                tokenManager = tokenManager,
                applicationUserRecipeViewModel = applicationUserRecipeViewModel
            )
        }
        composable(
            AppNavigationRoute.FetchOtherUserRecipeScreen.route + "/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")
                ?: "Надо придумать реализацию, если такого рецепта не существует"
            recipeId.let { recipe ->
                tokenManager.getToken()?.let { token ->
                    recipeViewModel.getRecipeById(
                        token,
                        recipe
                    )
                }
            }

            val recipeInfo = recipeViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                FetchOtherUserRecipeScreen(
                    navController = appNavigationController,
                    recipe = recipe,
                    applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
                    tokenManager = tokenManager
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
                tokenManager.getToken()?.let { token ->
                    recipeViewModel.getRecipeById(
                        token,
                        recipe
                    )
                }
            }

            val recipeInfo = recipeViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                FetchUserRecipeScreen(
                    navController = appNavigationController,
                    applicationUserRecipeViewModel = applicationUserRecipeViewModel,
                    recipe = recipe,
                    tokenManager = tokenManager
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
                tokenManager.getToken()?.let { token ->
                    recipeViewModel.getRecipeById(
                        token,
                        recipe
                    )
                }
            }

            val recipeInfo = recipeViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                EditRecipeScreen(
                    navController = appNavigationController,
                    applicationUserRecipeViewModel = applicationUserRecipeViewModel,
                    recipeStepsViewModel = recipeStepsViewModel,
                    recipe = recipe,
                    tokenManager = tokenManager
                )
            }
        }
        composable(AppNavigationRoute.CreatingRecipeScreen.route) {
            CreatingRecipeScreen(
                navController = appNavigationController,
                applicationUserRecipeViewModel = applicationUserRecipeViewModel,
                tokenManager = tokenManager
            )
        }
    }
}

