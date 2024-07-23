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
import com.den.culinarychest.presentation.ui.other.route.AppNavigationRoute
import com.den.culinarychest.presentation.other.screens.AuthorizationScreen
import com.den.culinarychest.presentation.other.screens.CreatingRecipeScreen
import com.den.culinarychest.presentation.other.screens.EditRecipeScreen
import com.den.culinarychest.presentation.other.screens.FetchOtherUserRecipeScreen
import com.den.culinarychest.presentation.other.screens.FetchUserRecipeScreen
import com.den.culinarychest.presentation.other.screens.RegistrationScreen
import com.den.culinarychest.presentation.ui.main.viewmodel.AuthorizationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.ManageOtherRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.CreatingRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.ManageRecipeUserViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.ProfileViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.ManageStepsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.RegistrationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.SearchViewModel
import com.example.culinarychest.data.data.repository.TokenManager


@Composable
fun AppNavigation(
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
                searchViewModel = searchViewModel,
                recipeDetailsViewModel = recipeDetailsViewModel,
                createdViewModel = createdViewModel,
                favoriteViewModel = favoriteViewModel,
                profileViewModel = profileViewModel,
                tokenManager = tokenManager,
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
                    recipeDetailsViewModel.getRecipeById(
                        token,
                        recipe
                    )
                    recipeDetailsViewModel.getFavoriteRecipeByRecipeId(token, recipe)
                }
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                FetchOtherUserRecipeScreen(
                    navController = appNavigationController,
                    recipe = recipe,
                    recipeDetailsViewModel = recipeDetailsViewModel,
                    manageOtherRecipeViewModel = manageOtherRecipeViewModel,
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
                    recipeDetailsViewModel.getRecipeById(
                        token,
                        recipe
                    )
                }
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                FetchUserRecipeScreen(
                    navController = appNavigationController,
                    manageRecipeUserViewModel = manageRecipeUserViewModel,
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
                    recipeDetailsViewModel.getRecipeById(
                        token,
                        recipe
                    )
                }
            }

            val recipeInfo = recipeDetailsViewModel.recipe.collectAsState().value
            recipeInfo.forEach { recipe ->
                EditRecipeScreen(
                    navController = appNavigationController,
                    manageRecipeUserViewModel = manageRecipeUserViewModel,
                    manageStepsViewModel = manageStepsViewModel,
                    recipe = recipe,
                    tokenManager = tokenManager
                )
            }
        }
        composable(AppNavigationRoute.CreatingRecipeScreen.route) {
            CreatingRecipeScreen(
                navController = appNavigationController,
                creatingRecipeViewModel = creatingRecipeViewModel,
                tokenManager = tokenManager
            )
        }
    }
}

