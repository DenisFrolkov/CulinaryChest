package com.den.culinarychest.presentation.ui.other.common.components.Item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.main.viewmodel.ImageViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.example.culinarychest.domain.model.recipe.Recipe

@Composable
fun RecipeItem(
    controller: NavController,
    imageViewModel: ImageViewModel,
    textRouteNavigation: String,
    recipe: Recipe,
    recipeDetailsViewModel: RecipeDetailsViewModel
) {

    imageViewModel.fetchRecipePhoto(recipe.imageUrl)
    val recipeImageUrl = imageViewModel.photoUrl.collectAsState().value

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
        .clickable {
            recipeDetailsViewModel.getFavoriteRecipeByRecipeId(
                recipe.recipeId
            )
            recipeDetailsViewModel.getRecipeById(recipeId = recipe.recipeId)
            controller.navigate("${textRouteNavigation}/${recipe.recipeId}")
        }
        .border(width = .15.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
        .background(SoftOrange, RoundedCornerShape(12.dp))) {
        Row {
            LoadImage(
                recipeImageUrl = recipeImageUrl
            )
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 12.dp)
            ) {
                Text(
                    text = recipe.title, style = TextStyle(
                        fontSize = 14.sp, color = SoftGray
                    )
                )
                Text(
                    text = if (recipe.ingredients.length > 160) recipe.ingredients.take(160) + "..." else recipe.ingredients,
                    style = TextStyle(
                        fontSize = 12.sp, color = SoftGray
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 23.dp, top = 6.dp, end = 8.dp, bottom = 4.dp)
        ) {
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_star_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = "${recipe.savedCount}",
                textFontSize = 12
            )
            Spacer(modifier = Modifier.width(8.dp))
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_time_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = recipe.preparationTime,
                textFontSize = 12
            )
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxWidth()
            ) {
                DisplayRecipeInfo(
                    iconRecipeInfo = painterResource(id = R.drawable.recipe_info_calendar_icon),
                    sizeRecipeInfoIcon = 16,
                    textRecipeInfo = recipe.creationDate.takeWhile { it != 'T' },
                    textFontSize = 10
                )
            }
        }
    }
}

@Composable
fun LoadImage(recipeImageUrl: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipeImageUrl)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(110.dp)
            .padding(start = 16.dp, top = 16.dp)
            .clip(shape = RoundedCornerShape(15.dp))
    )
}