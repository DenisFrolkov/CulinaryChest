package com.den.culinarychest.presentation.route

sealed class TopNavigationRoute(val route: String) {
    data object FavoriteScreen : TopNavigationRoute("favorite_screen")
    data object CreatedScreen : TopNavigationRoute("created_screen")
}