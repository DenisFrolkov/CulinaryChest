package com.den.culinarychest.presentation.ui.other.screens.recipes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.other.common.components.Item.RecipeItem
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.SearchViewModel
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.example.culinarychest.data.repository.TokenRepositoryImpl

@Composable
fun SearchScreen(
    navController: NavController,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    searchViewModel: SearchViewModel,
) {

    searchViewModel.getListRecipes(null)

    Search(
        controller = navController,
        searchViewModel = searchViewModel,
        recipeDetailsViewModel = recipeDetailsViewModel,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Search(
    controller: NavController,
    searchViewModel: SearchViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
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
                        recipeDetailsViewModel = recipeDetailsViewModel
                    )
                }
            }
        }

    }
}

@Composable
private fun SearchBarItem(
    searchViewModel: SearchViewModel,
    onTextChanged: (String) -> Unit,
) {

    var enteredSearchText by remember { mutableStateOf(TextFieldValue()) }
    var isHintVisible by remember { mutableStateOf(true) }
    var isHistoryVisible by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(enteredSearchText) {
        onTextChanged(enteredSearchText.text)
    }
    Column {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SoftOrange,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 0.1.dp,
                    color = SoftGray,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 16.dp)
        ) {
            BasicTextField(
                value = enteredSearchText,
                onValueChange = {
                    enteredSearchText = it
                    isHintVisible = it.text.isBlank()
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                singleLine = true,
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        searchViewModel.getListRecipes(enteredSearchText.text)
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused ->
                        isHistoryVisible = isFocused.isFocused
                    }
                    .align(Alignment.CenterStart)
                    .padding(end = 40.dp)
            )
            if (enteredSearchText.text.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.search_text),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = LightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}