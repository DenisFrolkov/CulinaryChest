package com.den.culinarychest.presentation.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.TextInput.SmallTextInput
import com.den.culinarychest.presentation.common.TextInput.NumberTextInput
import com.den.culinarychest.presentation.common.TextInput.RecipeDetailsTextInput
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

    var textTitle by remember { mutableStateOf("") }
    var textIngredient by remember { mutableStateOf("") }
    var textTime by remember { mutableStateOf("") }
    var textRecipeStep by remember { mutableStateOf("") }

    var countRecipeSteps by remember { mutableIntStateOf(1) }

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
                onTitleTextChanged = { textTitle = it },
                onIngredientsTextChanged = { textIngredient = it },
                onTimeTextChanged = { textTime = it }
            )
            DescribeStepsRecipe(
                count = countRecipeSteps,
                onCountChange = { newCount -> countRecipeSteps = newCount }
            )
        }
        items(countRecipeSteps) { recipeStepIndex ->
            val itemNumber = recipeStepIndex + 1
            Spacer(modifier = Modifier.height(height = 10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                NumberTextInput(
                    outputTextHint = "$itemNumber.",
                    onTextChanged = { textRecipeStep = it },
                    onTextValidation = { it.matches(Regex("[а-яА-Я0-9]+")) })
            }
        }
        item {
            Box(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 80.dp)
            ) {
                SaveRecipeButton()
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
        painter = painterResource(id = R.drawable.add_recipe_image),
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
    onTitleTextChanged: (String) -> Unit,
    onIngredientsTextChanged: (String) -> Unit,
    onTimeTextChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        RecipeDetailsTextInput(
            outputTextHint = stringResource(R.string.enter_title_recipe),
            onTextChanged = onTitleTextChanged,
            onTextValidation = { it.matches(Regex("[а-яА-Я0-9]+")) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        RecipeDetailsTextInput(
            outputTextHint = stringResource(R.string.enter_ingredients_recipe),
            onTextChanged = onIngredientsTextChanged,
            onTextValidation = { it.matches(Regex("[а-яА-Я0-9]+")) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.enter_time_recipe),
                style = TextStyle(fontSize = 14.sp, color = SoftGray),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(end = 4.dp)
            )
            SmallTextInput(
                outputTextHint = stringResource(R.string.in_minutes_recipe),
                onTextChanged = onTimeTextChanged,
                onTextValidation = { it.matches(Regex("[0-9]+")) }
            )
        }
    }
}

@Composable
fun DescribeStepsRecipe(
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
            text = stringResource(R.string.describe_steps_in_preparing),
            style = TextStyle(fontSize = 14.sp, color = SoftGray),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(end = 4.dp)
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 28.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_step_button_icon),
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
fun SaveRecipeButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.save_recipe_text),
            style = TextStyle(
                fontSize = 16.sp,
                color = SoftGray
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}



