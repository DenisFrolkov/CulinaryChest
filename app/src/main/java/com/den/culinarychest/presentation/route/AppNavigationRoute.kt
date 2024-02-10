package com.den.culinarychest.presentation.route

sealed class AppNavigationRoute(val route: String) {
    object AuthorizationScreen : AppNavigationRoute("authorization_screen")
    object RegistrationScreen : AppNavigationRoute("registration_screen")
    object BottomAppNavigationBar : AppNavigationRoute("bottom_navigation_bar")
    object RecipeScreen : AppNavigationRoute("recipe_screen")
    object CreatingRecipeScreen : AppNavigationRoute("creating_recipe_screen")
}