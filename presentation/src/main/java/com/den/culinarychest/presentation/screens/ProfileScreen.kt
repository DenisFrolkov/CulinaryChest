package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.ProfileStatisticsItem
import com.den.culinarychest.presentation.common.Button.SettingButton
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.TokenManager

@Composable
fun ProfileScreen(
    navController: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    recipeViewModel: RecipeViewModel,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager
) {

    val userInfo = applicationUserViewModel.userInfoResult.collectAsState().value

    val applicationUserRecipeSize = applicationUserRecipeViewModel.applicationUserRecipes.collectAsState().value.size
    val favoriteRecipeList = applicationUserFavoriteRecipeViewModel.userFavoriteRecipes.collectAsState().value
    tokenManager.getToken()?.let { token ->
        val recipeIds = favoriteRecipeList.map { it.recipeId.toString() }
        recipeViewModel.getRecipesByIds(token, recipeIds)
    }

    val applicationUserFavoriteRecipeSize =
        recipeViewModel.recipesById.collectAsState().value.size
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SoftOrange)
                .border(width = 0.1.dp, color = SoftGray)
        ) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size = 100.dp)
                            .background(color = SoftGray, shape = RoundedCornerShape(size = 50.dp))
                    ) { }
                    userInfo?.let {
                        Text(
                            text = it.userName,
                            style = TextStyle(
                                color = SoftGray,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .align(Alignment.CenterHorizontally),
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp)
                        .padding(horizontal = 18.dp)
                ) {
                    ProfileStatisticsItem(
                        textStatistic = stringResource(R.string.favorite_recipe_text),
                        numberStatistic = "$applicationUserFavoriteRecipeSize"
                    )
                    ProfileStatisticsItem(
                        textStatistic = stringResource(R.string.created_recipe_text),
                        numberStatistic = "$applicationUserRecipeSize"
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 88.dp)
            ) {
                SettingButton(
                    controller = navController,
                    navigationText = AppNavigationRoute.AuthorizationScreen.route,
                    borderColor = Color.Red,
                    textButton = stringResource(R.string.exit_text),
                    textColor = Color.Red,
                    tokenManager = tokenManager
                )
            }
        }
    }
}

