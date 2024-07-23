package com.den.culinarychest.presentation.other.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.other.screens.common.CreatedScreen
import com.den.culinarychest.presentation.ui.other.screens.common.FavoriteScreen
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.example.culinarychest.data.repository.TokenManager
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HorizontalPagerScreen(
    navController: NavController,
    createdViewModel: CreatedViewModel,
    favoriteViewModel: FavoriteViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    tokenManager: TokenManager
) {
    HorizontalPager(
        controller = navController,
        createdViewModel = createdViewModel,
        favoriteViewModel = favoriteViewModel,
        recipeDetailsViewModel = recipeDetailsViewModel,
        tokenManager = tokenManager
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPager(
    controller: NavController,
    createdViewModel: CreatedViewModel,
    favoriteViewModel: FavoriteViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    tokenManager: TokenManager
) {

    tokenManager.getToken()?.let {
        favoriteViewModel.getListFavoriteRecipesUser(it)
        createdViewModel.getListRecipesUser(it)
    }

    val pagerState = rememberPagerState(pageCount = { 2 })

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceAround
            ) {
                TopBarButtonItem(
                    isSelected = pagerState.currentPage == 0,
                    textButton = stringResource(R.string.created_recipe_text),
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
                TopBarButtonItem(
                    isSelected = pagerState.currentPage == 1,
                    textButton = stringResource(R.string.favorite_text),
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
            }
        }
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> CreatedScreen(
                    controller = controller,
                    createdViewModel = createdViewModel,
                    recipeDetailsViewModel = recipeDetailsViewModel,
                    tokenManager = tokenManager
                )

                1 -> FavoriteScreen(
                    controller = controller,
                    favoriteViewModel = favoriteViewModel,
                    recipeDetailsViewModel = recipeDetailsViewModel,
                    tokenManager = tokenManager
                )
            }
        }
    }
}

@Composable
private fun TopBarButtonItem(
    isSelected: Boolean,
    textButton: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 159.dp, height = 46.dp)
            .background(color = SoftOrange, shape = RoundedCornerShape(size = 12.dp))
            .border(
                width = if (isSelected) 0.50.dp else 0.15.dp,
                color = SoftGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                color = if (isSelected) Color.Black else SoftGray,
                fontSize = 14.sp,
            )
        )
    }
}