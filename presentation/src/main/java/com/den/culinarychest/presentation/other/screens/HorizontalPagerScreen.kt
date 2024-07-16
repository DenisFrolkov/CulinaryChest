package com.den.culinarychest.presentation.other.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.other.common.Item.TopBarButtonItem
import com.den.culinarychest.presentation.main.viewmodel.HorizontalPagerViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeOwnershipViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HorizontalPagerScreen(
    navController: NavController,
    horizontalPagerViewModel: HorizontalPagerViewModel,
    recipeOwnershipViewModel: RecipeOwnershipViewModel,
    tokenManager: TokenManager
) {
    HorizontalPager(
        controller = navController,
        horizontalPagerViewModel = horizontalPagerViewModel,
        recipeOwnershipViewModel = recipeOwnershipViewModel,
        tokenManager = tokenManager
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPager(
    controller: NavController,
    horizontalPagerViewModel: HorizontalPagerViewModel,
    recipeOwnershipViewModel: RecipeOwnershipViewModel,
    tokenManager: TokenManager
) {

    tokenManager.getToken()?.let {
        horizontalPagerViewModel.getApplicationUserFavoriteRecipes(it)
        horizontalPagerViewModel.getApplicationUserRecipes(it)
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
                    horizontalPagerViewModel = horizontalPagerViewModel,
                    recipeOwnershipViewModel = recipeOwnershipViewModel,
                    tokenManager = tokenManager
                )

                1 -> FavoriteScreen(
                    controller = controller,
                    horizontalPagerViewModel = horizontalPagerViewModel,
                    recipeOwnershipViewModel = recipeOwnershipViewModel,
                    tokenManager = tokenManager
                )
            }
        }
    }
}