package com.den.culinarychest.presentation.navigation

sealed class NavigationRoute(val route: String) {
    object AuthorizationScreen : NavigationRoute("authorization_screen")
    object RegistrationScreen : NavigationRoute("registration_screen")
    object BottomNavigationBar : NavigationRoute("bottom_navigation_bar")
}