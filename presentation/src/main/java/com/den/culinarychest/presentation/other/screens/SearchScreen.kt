package com.den.culinarychest.presentation.other.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.other.common.Item.RecipeItem
import com.den.culinarychest.presentation.other.common.Item.SearchBarItem
import com.den.culinarychest.presentation.other.route.AppNavigationRoute
import com.den.culinarychest.presentation.other.ui.theme.SoftGray
import com.den.culinarychest.presentation.other.ui.theme.SoftPink
import com.den.culinarychest.presentation.main.viewmodel.RecipeDetailsViewModel
import com.den.culinarychest.presentation.main.viewmodel.SearchViewModel
import com.example.culinarychest.data.data.repository.TokenManager

@Composable
fun SearchScreen(
    navController: NavController,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    searchViewModel: SearchViewModel,
    tokenManager: TokenManager
) {

    tokenManager.getToken()?.let {
        searchViewModel.getListRecipes(it, null)
    }

    Search(
        controller = navController,
        searchViewModel = searchViewModel,
        recipeDetailsViewModel = recipeDetailsViewModel,
        tokenManager = tokenManager
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Search(
    controller: NavController,
    searchViewModel: SearchViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    tokenManager: TokenManager
) {
    var searchText by remember { mutableStateOf("") }

    val recipeList = searchViewModel.listRecipes.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftPink)
            .padding(bottom = 40.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            SearchBarItem(
                searchViewModel = searchViewModel,
                tokenManager = tokenManager,
                onTextChanged = { text -> searchText = text }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (recipeList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = SoftGray,
                    strokeWidth = 1.5.dp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
            ) {
                items(recipeList) { recipe ->
                    RecipeItem(
                        controller = controller,
                        textRouteNavigation = AppNavigationRoute.FetchOtherUserRecipeScreen.route,
                        recipe = recipe,
                        tokenManager = tokenManager,
                        recipeDetailsViewModel = recipeDetailsViewModel
                    )
                }
            }
        }

    }
}