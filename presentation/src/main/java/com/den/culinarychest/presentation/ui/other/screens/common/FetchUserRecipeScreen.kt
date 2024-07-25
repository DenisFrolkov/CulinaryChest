package com.den.culinarychest.presentation.ui.other.screens.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.other.common.components.Item.DisplayRecipeInfo
import com.den.culinarychest.presentation.ui.other.common.components.Item.StepRecipeItem
import com.den.culinarychest.presentation.ui.other.common.components.Item.CreateImageLoader
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageRecipeUserViewModel
import com.example.culinarychest.data.repository.TokenRepositoryImpl
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.step.Step

@Composable
fun FetchUserRecipeScreen(
    navController: NavController,
    manageRecipeUserViewModel: ManageRecipeUserViewModel,
    recipe: Recipe,
) {
    FetchUserRecipe(
        controller = navController,
        manageRecipeUserViewModel = manageRecipeUserViewModel,
        recipe = recipe,
    )
}

@Composable
fun FetchUserRecipe(
    controller: NavController,
    manageRecipeUserViewModel: ManageRecipeUserViewModel,
    recipe: Recipe,
) {

    val recipeIngredients = """ ${recipe.ingredients} """.trimIndent()

    val dropDownMenuItems = arrayOf(
        "Редактировать",
        "Удалить"
    )

    var mappingDropdownMenu by remember { mutableStateOf(false) }

    Column {
        FetchUserRecipeTopBar(
            controller = controller,
            mappingDropdownMenu = mappingDropdownMenu,
            onClickParametersIcon = { newValue -> mappingDropdownMenu = newValue }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            item {
                FetchUserRecipeImage(
                    context = LocalContext.current,
                    recipeImageUrl = recipe.imageUrl
                )
                FetchUserRecipeMiniInformation(recipe = recipe)
                FetchUserRecipeTitle(recipe = recipe)
                FetchUserRecipeIngredient(recipeIngredients = recipeIngredients)
                FetchUserRecipeSteps(recipeSteps = recipe.steps)
            }

        }
    }
    if (mappingDropdownMenu) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp)
        ) {
            RecipeDropDownMenu(
                dropDownMenuItems = dropDownMenuItems,
                onClickParameters = { newValueParameters ->
                    mappingDropdownMenu = newValueParameters
                },
                onItemClick = { selectedItem ->
                    when (selectedItem) {
                        "Редактировать" -> {
                            controller.navigate("${AppNavigationRoute.EditRecipeScreen.route}/${recipe.recipeId}")
                        }

                        "Удалить" -> {
                            controller.popBackStack()
                            manageRecipeUserViewModel.deleteRecipeUser(
                                recipe.recipeId
                            )
                        }
                    }
                }
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FetchUserRecipeTopBar(
    controller: NavController,
    mappingDropdownMenu: Boolean,
    onClickParametersIcon: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SoftOrange)
            .border(width = 0.1.dp, color = SoftGray)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    controller.popBackStack()
                }
        )
        Image(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onClickParametersIcon(!mappingDropdownMenu)
                },
            painter = painterResource(id = R.drawable.recipe_paraments_icon),
            contentDescription = null
        )
    }
}

@Composable
fun FetchUserRecipeImage(
    context: Context,
    recipeImageUrl: String
) {
    val desiredPath = recipeImageUrl.substringAfter("/wwwroot/")
    val imageUrl = "https://zany-meme-jp7rjw5xjwpfpv47-7286.app.github.dev/images/${desiredPath}"
    val imageLoader = CreateImageLoader(context)

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        imageLoader = imageLoader
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(400.dp)
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(15.dp))
        )
    }
}

@Composable
fun FetchUserRecipeMiniInformation(
    recipe: Recipe
) {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 6.dp, end = 8.dp)
    ) {
        DisplayRecipeInfo(
            iconRecipeInfo = painterResource(id = R.drawable.recipe_info_star_icon),
            sizeRecipeInfoIcon = 24,
            textRecipeInfo = "${recipe.savedCount}",
            textFontSize = 16
        )
        Spacer(modifier = Modifier.width(8.dp))
        DisplayRecipeInfo(
            iconRecipeInfo = painterResource(id = R.drawable.recipe_info_time_icon),
            sizeRecipeInfoIcon = 24,
            textRecipeInfo = "${recipe.preparationTime} мин",
            textFontSize = 16
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 18.dp),
            horizontalArrangement = Arrangement.End
        ) {
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_calendar_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = recipe.creationDate.takeWhile { it != 'T' },
                textFontSize = 12
            )
        }
    }
}

@Composable
fun FetchUserRecipeTitle(
    recipe: Recipe
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        text = recipe.title,
        style = TextStyle(
            fontSize = 18.sp,
            color = SoftGray
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun FetchUserRecipeIngredient(
    recipeIngredients: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            modifier = Modifier.padding(all = 6.dp),
            text = stringResource(R.string.ingredients_text),
            style = TextStyle(
                fontSize = 14.sp,
                color = SoftGray
            )
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style =
                    SpanStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                ) {
                    append(recipeIngredients.replace(", ", "\n"))
                }
            },
            modifier = Modifier
                .padding(start = 14.dp, end = 6.dp, bottom = 6.dp)
        )
    }
}

@Composable
fun FetchUserRecipeSteps(
    recipeSteps: List<Step>
) {
    Text(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 6.dp),
        text = stringResource(R.string.preparation_steps_text),
        style = TextStyle(
            fontSize = 16.sp,
            color = SoftGray
        )
    )
    recipeSteps.forEach { step ->
        StepRecipeItem(
            numberStep = "${step.order}",
            textStep = step.description
        )
    }
}

@Composable
fun RecipeDropDownMenu(
    dropDownMenuItems: Array<String>,
    onClickParameters: (Boolean) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(bottomStart = 12.dp))
    ) {
        dropDownMenuItems.forEach { dropDownMenuItem ->
            Text(
                text = dropDownMenuItem,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onClickParameters(false)
                        onItemClick(dropDownMenuItem)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}