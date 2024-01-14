package com.den.culinarychest.presentation.bottomnavigation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.den.culinarychest.presentation.common.BottomNavigationItem
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R
import com.den.culinarychest.presentation.screens.ChestScreen
import com.den.culinarychest.presentation.screens.ProfileScreen
import com.den.culinarychest.presentation.screens.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
) {
    val navBottomController = rememberNavController()
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
                    selectedIcon = painterResource (id = R.drawable.search_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.SearchScreen.route
                )
                BottomNavigationItem(
                    selectedIcon = painterResource(id = R.drawable.favorite_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.ChestScreen.route
                )
                BottomNavigationItem(
                    selectedIcon = painterResource(id = R.drawable.profile_icon),
                    navBottomBarController = navBottomController,
                    buttonNavigation = BottomNavigationRoute.ProfileScreen.route
                )
            }
        }
    ) {
        NavHost(
            navController = navBottomController,
            startDestination = BottomNavigationRoute.SearchScreen.route
        ) {
            composable(BottomNavigationRoute.SearchScreen.route) {
                SearchScreen()
            }
            composable(BottomNavigationRoute.ChestScreen.route) {
                ChestScreen()
            }
            composable(BottomNavigationRoute.ProfileScreen.route) {
                ProfileScreen()
            }
        }
    }
}