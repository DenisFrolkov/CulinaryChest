package com.den.culinarychest.presentation.navigation.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.den.culinarychest.R
import com.den.culinarychest.presentation.navigation.topNavigationBar.TopNavigationBar
import com.den.culinarychest.presentation.route.BottomNavigationRoute
import com.den.culinarychest.presentation.screens.ProfileScreen
import com.den.culinarychest.presentation.screens.SearchScreen
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.сlasses.data_class.BottomNavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val bottomController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            BottomNavigationRoute.SearchScreen.route, R.drawable.bottombnavigation_search_icon, stringResource(R.string.search_text)
        ),
        BottomNavigationItem(
            BottomNavigationRoute.TopNavigationBar.route, R.drawable.bottombnavigation_favorites_icon, stringResource(R.string.favorite_text)
        ),
        BottomNavigationItem(
            BottomNavigationRoute.ProfileScreen.route, R.drawable.bottombnavigation_profile_icon, stringResource(R.string.profile_text)
        ),
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SoftOrange,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .border(
                        width = 0.15.dp,
                        color = SoftGray,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
                    .height(54.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = SoftOrange)
                ) {
                    bottomNavigationItems.forEachIndexed { bottomNavigationIndex, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = selectedItemIndex == bottomNavigationIndex,
                            onClick = {
                                selectedItemIndex = bottomNavigationIndex
                                bottomController.navigate(bottomNavigationItem.route)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = SoftOrange,
                            ),
                            icon = {
                                Icon(
                                    modifier = Modifier.size(if (bottomNavigationIndex == selectedItemIndex) 36.dp else 30.dp),
                                    painter = painterResource(id = bottomNavigationItem.icon),
                                    contentDescription = null,
                                    tint = SoftGray
                                )
                            }
                        )
                    }
                }
            }
        },
    ) {
        NavHost(
            navController = bottomController,
            startDestination = BottomNavigationRoute.TopNavigationBar.route
        ) {
            composable(BottomNavigationRoute.SearchScreen.route) {
                SearchScreen(controller = navController)
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