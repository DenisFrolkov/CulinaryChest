package com.den.culinarychest.presentation.navigaion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.den.culinarychest.presentation.screens.AuthorizationScreen
import com.den.culinarychest.presentation.screens.RegistrationScreen
import com.den.culinarychest.presentation.screens.SearchScreen

@Composable
fun NavController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationRoute.AuthorizationScreen.route)
    {
        composable(NavigationRoute.AuthorizationScreen.route){
            AuthorizationScreen(navController = navController)
        }
        composable(NavigationRoute.RegistrationScreen.route){
            RegistrationScreen(navController = navController)
        }
        composable(NavigationRoute.SearchScreen.route){
            SearchScreen(navController = navController)
        }
    }
}