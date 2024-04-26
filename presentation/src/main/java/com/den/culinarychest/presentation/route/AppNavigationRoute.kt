package com.den.culinarychest.presentation.route

sealed class AppNavigationRoute(val route: String) {
    object AuthorizationScreen : AppNavigationRoute("authorization_screen")
    object RegistrationScreen : AppNavigationRoute("registration_screen")
    object BottomAppNavigationBar : AppNavigationRoute("bottom_navigation_bar")
    object FetchOtherUserRecipeScreen : AppNavigationRoute("fetch_other_user_recipe_screen")
    object FetchUserRecipeScreen : AppNavigationRoute("fetch_user_recipe_screen")
    object EditRecipeScreen : AppNavigationRoute("edit_recipe_screen")
    object CreatingRecipeScreen : AppNavigationRoute("creating_recipe_screen")
}