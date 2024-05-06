package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.example.culinarychest.data.data.TokenManager
import com.example.culinarychest.domain.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import java.time.LocalDateTime

@Composable
fun CreatingRecipeScreen(
    navController: NavController,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    tokenManager: TokenManager
) {
    CreatingRecipe(
        navController = navController,
        applicationUserRecipeViewModel = applicationUserRecipeViewModel,
        tokenManager = tokenManager
    )
}


@SuppressLint("NewApi")
@Composable
fun CreatingRecipe(
    navController: NavController,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    tokenManager: TokenManager
) {

    var textTitle by remember { mutableStateOf("") }
    var textIngredient by remember { mutableStateOf("") }
    var textRecipeStep by remember { mutableStateOf("") }
    var numberTextRecipeStep by remember { mutableStateOf("") }
    var textPreparationTime by remember { mutableStateOf("") }
    val steps = remember { mutableStateListOf<CreateStep>() }
    var countRecipeSteps by remember { mutableIntStateOf(1) }

    fun addStep(textRecipeStep: String, numberTextRecipeStep: String) {
        val newStep = CreateStep(textRecipeStep, numberTextRecipeStep)
        steps.add(newStep)
    }

    val createRecipe = CreateRecipe(
        title = textTitle,
        ingredients = textIngredient,
        recipeImage = "Пока что ничего",
        steps = steps.toList(),
        creationDate = LocalDateTime.now().toString(),
        preparationTime = textPreparationTime,
        savedCount = countRecipeSteps
    )

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
                onTimeTextChanged = { textPreparationTime = it }
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
                    onTextChanged = { newTextRecipeStep -> textRecipeStep = newTextRecipeStep },
                    onNumberTextChanged = { newNumberTextRecipeStep -> numberTextRecipeStep = newNumberTextRecipeStep },
                    onTextValidation = { it.matches(Regex("[а-яА-Я0-9]+")) },
                    onEnterPressed = { addStep(textRecipeStep, numberTextRecipeStep) }
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .padding(horizontal = 80.dp)
            ) {
                CreatingRecipeSaveButton(
                    controller = navController,
                    navigationRoute = AppNavigationRoute.BottomAppNavigationBar.route,
                    applicationUserRecipeViewModel = applicationUserRecipeViewModel,
                    tokenManager = tokenManager,
                    recipeInfo = createRecipe,
                    buttonText = stringResource(id = R.string.save_recipe_text),
                    colorButtonText = SoftGray,
                    buttonColor = SoftOrange
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun TopBar(navController: NavController) {
    Row(
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
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    navController.popBackStack()
                }
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
private fun CreatingRecipeSaveButton(
    controller: NavController,
    navigationRoute: String,
    applicationUserRecipeViewModel: ApplicationUserRecipeViewModel,
    tokenManager: TokenManager,
    recipeInfo: CreateRecipe,
    buttonText: String,
    colorButtonText: Color,
    buttonColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (buttonColor == SoftOrange) 1f else 0.5f)
            .background(color = buttonColor, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                tokenManager
                    .getToken()
                    ?.let {
                        applicationUserRecipeViewModel.createApplicationUserRecipe(
                            it, recipeInfo
                        )
                    }
//                controller.navigate(navigationRoute)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buttonText,
            style = TextStyle(
                fontSize = 16.sp,
                color = colorButtonText
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}





