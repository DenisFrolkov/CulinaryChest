package com.den.culinarychest.presentation.navigation.appNavigation

import androidx.compose.runtime.Composable
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

@Composable
fun AppNavigation() {
    val appNavigationController = rememberNavController()
    NavHost(
        navController = appNavigationController,
        startDestination = AppNavigationRoute.EditRecipeScreen.route)
    {
        composable(AppNavigationRoute.AuthorizationScreen.route){
            AuthorizationScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.RegistrationScreen.route){
            RegistrationScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.BottomAppNavigationBar.route){
            BottomNavigationBar(navController = appNavigationController)
        }
        composable(AppNavigationRoute.FetchOtherUserRecipeScreen.route){
            FetchOtherUserRecipeScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.FetchUserRecipeScreen.route){
            FetchUserRecipeScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.EditRecipeScreen.route){
            EditRecipeScreen(navController = appNavigationController)
        }
        composable(AppNavigationRoute.CreatingRecipeScreen.route){
            CreatingRecipeScreen(navController = appNavigationController)
        }
    }
}

