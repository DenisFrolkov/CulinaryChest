package com.den.culinarychest.presentation.bottomnavigation

sealed class BottomNavigationRoute(val route: String) {
    object SearchScreen : BottomNavigationRoute("search_screen")
    object ChestScreen : BottomNavigationRoute("chest_screen")
    object ProfileScreen : BottomNavigationRoute("profile_screen")
}