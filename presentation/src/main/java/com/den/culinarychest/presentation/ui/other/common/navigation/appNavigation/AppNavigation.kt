//@file:Suppress("DEPRECATION")
package com.den.culinarychest.presentation.ui.other.common.navigation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.den.culinarychest.presentation.ui.other.common.navigation.bottomNavigation.BottomNavigationBar
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.other.screens.auth.AuthorizationScreen
import com.den.culinarychest.presentation.ui.other.screens.common.CreatingRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.EditRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.FetchOtherUserRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.common.FetchUserRecipeScreen
import com.den.culinarychest.presentation.ui.other.screens.auth.RegistrationScreen
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.AuthorizationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageOtherRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatingRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageRecipeUserViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.profile.ProfileViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageStepsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.RegistrationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.SearchViewModel
import com.example.culinarychest.data.repository.TokenManager


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

