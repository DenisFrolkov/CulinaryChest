package com.den.culinarychest.presentation.navigaion

sealed class NavigationRoute(val route: String) {
    object AuthorizationScreen : NavigationRoute("authorization_screen")
    object RegistrationScreen : NavigationRoute("registration_screen")
    object SearchScreen : NavigationRoute("search_screen")
}