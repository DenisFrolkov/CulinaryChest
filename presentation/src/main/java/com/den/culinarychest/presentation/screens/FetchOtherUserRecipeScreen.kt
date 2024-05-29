package com.den.culinarychest.presentation.screens

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.den.culinarychest.presentation.common.Item.DisplayRecipeInfo
import com.den.culinarychest.presentation.common.Item.StepRecipeItem
import com.den.culinarychest.presentation.common.Item.createImageLoader
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import java.time.LocalDateTime

@Composable
fun FetchOtherUserRecipeScreen(
    navController: NavController,
    recipe: Recipe,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager
) {
    FetchOtherUserRecipe(
        controller = navController,
        recipe = recipe,
        applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
        tokenManager = tokenManager
    )
}

@Composable
fun FetchOtherUserRecipe(
    controller: NavController,
    recipe: Recipe,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager
) {

    var clickElementLike by remember {
        mutableStateOf(false)
    }
    val favoriteRecipeByRecipeId by applicationUserFavoriteRecipeViewModel.favoriteRecipeByRecipeId.collectAsState()

    Column {
        FetchOtherUserRecipeTopBar(
            controller = controller,
            recipeId = recipe.recipeId,
            favoriteRecipeByRecipeId = favoriteRecipeByRecipeId,
            clickElement = clickElementLike,
            applicationUserFavoriteRecipeViewModel = applicationUserFavoriteRecipeViewModel,
            tokenManager = tokenManager,
            passClickElement = { clickElementLike = it }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            item {
                FetchOtherUserRecipeImage(
                    context = LocalContext.current,
                    recipeImageUrl = recipe.imageUrl
                )
                FetchOtherUserRecipeMiniInformation(recipe = recipe)
                FetchOtherUserRecipeTitle(recipe = recipe)
                FetchOtherUserRecipeIngredient(recipe = recipe)
                FetchOtherUserRecipeSteps(recipe = recipe)
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun FetchOtherUserRecipeTopBar(
    controller: NavController,
    recipeId: String,
    favoriteRecipeByRecipeId: FavoriteRecipe?,
    applicationUserFavoriteRecipeViewModel: ApplicationUserFavoriteRecipeViewModel,
    tokenManager: TokenManager,
    clickElement: Boolean,
    passClickElement: (Boolean) -> Unit
) {

    var clickElement1 by remember {
        mutableStateOf(clickElement)
    }

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
        if (clickElement1 != ("${favoriteRecipeByRecipeId?.recipeId}" != recipeId)) {
            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        tokenManager
                            .getToken()
                            ?.let {
                                applicationUserFavoriteRecipeViewModel.createApplicationUserFavoriteRecipes(
                                    it,
                                    recipeId.toInt(),
                                    CreateFavoriteRecipe(
                                        LocalDateTime
                                            .now()
                                            .toString()
                                    )
                                )
                            }
                        clickElement1 = true
                        if ("${favoriteRecipeByRecipeId?.recipeId}" != recipeId) clickElement1 = true else clickElement1 = false
                        passClickElement("${favoriteRecipeByRecipeId?.recipeId}" == recipeId)
                    },
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = SoftGray
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        tokenManager
                            .getToken()
                            ?.let {
                                applicationUserFavoriteRecipeViewModel.deleteApplicationUserFavoriteRecipe(
                                    it, recipeId
                                )
                            }
                        if ("${favoriteRecipeByRecipeId?.recipeId}" == recipeId) clickElement1 = true else clickElement1 = false
                        passClickElement("${favoriteRecipeByRecipeId?.recipeId}" != recipeId)
                    },
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@Composable
fun FetchOtherUserRecipeImage(
    context: Context,
    recipeImageUrl: String
    ) {
        val desiredPath = recipeImageUrl.substringAfter("/wwwroot/")
        val imageUrl = "https://10.0.2.2:7286/${desiredPath}"
        val imageLoader = createImageLoader(context)

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
                    .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp))
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    .height(400.dp)
            )
        }
}

@Composable
fun FetchOtherUserRecipeMiniInformation(
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
fun FetchOtherUserRecipeTitle(
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
fun FetchOtherUserRecipeIngredient(
    recipe: Recipe
) {

    val recipeIngredients = """ ${recipe.ingredients} """.trimIndent()

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
            modifier = Modifier
                .padding(start = 14.dp, end = 6.dp, bottom = 6.dp),
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
            }
        )
    }
}

@Composable
fun FetchOtherUserRecipeSteps(
    recipe: Recipe
) {
    Text(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 6.dp),
        text = stringResource(R.string.preparation_steps_text),
        style = TextStyle(
            fontSize = 16.sp,
            color = SoftGray
        )
    )
    recipe.steps.forEach { step ->
        StepRecipeItem(
            numberStep = "${step.order}",
            textStep = step.description
        )
    }
}



