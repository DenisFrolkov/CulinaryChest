package com.den.culinarychest.presentation.bottomnavigation

sealed class BottomNavigationRoute(val route: String) {
    object SearchScreen : BottomNavigationRoute("search_screen")
    object TopNavigationBar : BottomNavigationRoute("top_navigation_bar")
    object ProfileScreen : BottomNavigationRoute("profile_screen")
}