package com.den.culinarychest.presentation.screens

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.TextInput.MiniTextInput
import com.den.culinarychest.presentation.common.TextInput.NumberTextInput
import com.den.culinarychest.presentation.common.TextInput.TextInput
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun CreatingRecipeScreen(
    navController: NavController
) {
    CreatingRecipe(
        navController = navController
    )
}

@Composable
fun CreatingRecipe(
    navController: NavController
) {

    var titleText by remember { mutableStateOf("") }
    var ingredientsText by remember { mutableStateOf("") }
    var timeText by remember { mutableStateOf("") }
    var stepRecipeText by remember { mutableStateOf("") }

    var count by remember { mutableIntStateOf(3) }

    val stringResource = LocalContext.current.resources

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        item {
            TopBar(navController = navController)
            AddRecipePhoto()
            RecipeInputs(
                stringResource = stringResource,
                onTitleTextChanged = { titleText = it },
                onIngredientsTextChanged = { ingredientsText = it }
            ) { timeText = it }
            DescribeSteps(
                stringResource = stringResource,
                count = count,
                onCountChange = { newCount -> count = newCount }
            )
        }
        items(count) { index ->
            val itemNumber = index + 1
            Spacer(modifier = Modifier.height(height = 10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                NumberTextInput(
                    inputText = "$itemNumber.",
                    onTextChanged = { stepRecipeText = it },
                    validation = { it.matches(Regex("[а-яА-Я0-9]+")) })
            }
        }
        item {
            Box(
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            ) {
                SaveButton()
            }
        }
    }
}

@Composable
fun TopBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SoftOrange)
            .border(width = 0.1.dp, color = SoftGray)
            .padding(horizontal = 12.dp, vertical = 14.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable { navController.popBackStack() }
        )
    }
}

@Composable
fun AddRecipePhoto() {
    Image(
        painter = painterResource(id = R.drawable.add_recipe_photo),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .clickable { /* Handle photo addition */ }
    )
}

@Composable
fun RecipeInputs(
    stringResource: Resources,
    onTitleTextChanged: (String) -> Unit,
    onIngredientsTextChanged: (String) -> Unit,
    onTimeTextChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        TextInput(
            inputText = stringResource.getString(R.string.enter_title_recipe),
            onTextChanged = onTitleTextChanged,
            validation = { it.matches(Regex("[а-яА-Я0-9]+")) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextInput(
            inputText = stringResource.getString(R.string.enter_ingredients_recipe),
            onTextChanged = onIngredientsTextChanged,
            validation = { it.matches(Regex("[а-яА-Я0-9]+")) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource.getString(R.string.enter_time_recipe),
                style = TextStyle(fontSize = 14.sp, color = SoftGray),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(end = 4.dp)
            )
            MiniTextInput(
                inputText = stringResource.getString(R.string.in_minutes_recipe),
                onTextChanged = onTimeTextChanged,
                validation = { it.matches(Regex("[0-9]+")) }
            )
        }
    }
}

@Composable
fun DescribeSteps(
    stringResource: Resources,
    count: Int,
    onCountChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 6.dp)
    ) {
        Text(
            text = stringResource.getString(R.string.describe_steps_in_preparing),
            style = TextStyle(fontSize = 14.sp, color = SoftGray),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(end = 4.dp)
        )
        Box(modifier = Modifier.padding(end = 10.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.add_button_icon),
                contentDescription = null,
                tint = SoftGray,
                modifier = Modifier
                    .size(size = 36.dp)
                    .clickable { onCountChange(count + 1) }
            )
        }
    }
}

@Composable
fun SaveButton() {
    Box(
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { }
    ) {
        Text(
            text = "Cохранить рецепт",
            style = TextStyle(
                fontSize = 14.sp,
                color = SoftGray
            ),
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)
        )
    }
}



