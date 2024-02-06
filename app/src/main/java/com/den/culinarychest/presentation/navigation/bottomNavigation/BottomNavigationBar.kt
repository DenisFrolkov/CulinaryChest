package com.den.culinarychest.presentation.navigation.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.den.culinarychest.presentation.common.BottomNavigationItem
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R
import com.den.culinarychest.presentation.navigation.topNavigationBar.TopNavigationBar
import com.den.culinarychest.presentation.route.BottomNavigationRoute
import com.den.culinarychest.presentation.screens.ProfileScreen
import com.den.culinarychest.presentation.screens.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBottomController = rememberNavController()
    var searchSelected by remember { mutableStateOf(true) }
    var topBarSelected by remember { mutableStateOf(false) }
    var profileSelected by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .background(
                        color = SoftOrange,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .border(
                        width = 0.15.dp,
                        color = SoftGray,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                BottomNavigationItem(
                    isSelected = searchSelected,
                    selectedIcon = painterResource (id = R.drawable.search_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.SearchScreen.route
                ){
                    searchSelected = true
                    topBarSelected = false
                    profileSelected = false
                }
                BottomNavigationItem(
                    isSelected = topBarSelected,
                    selectedIcon = painterResource(id = R.drawable.favorite_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.TopNavigationBar.route
                ){
                    searchSelected = false
                    topBarSelected = true
                    profileSelected = false
                }
                BottomNavigationItem(
                    isSelected = topBarSelected,
                    selectedIcon = painterResource(id = R.drawable.profile_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.ProfileScreen.route
                ){
                    topBarSelected = false
                    searchSelected = false
                    profileSelected = true
                }
            }
        }
    ) {
        NavHost(
            navController = navBottomController,
            startDestination = BottomNavigationRoute.SearchScreen.route
        ) {
            composable(BottomNavigationRoute.SearchScreen.route) {
                SearchScreen(navController = navController)
            }
            composable(BottomNavigationRoute.TopNavigationBar.route) {
                TopNavigationBar(navController = navController)
            }
            composable(BottomNavigationRoute.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    }
}