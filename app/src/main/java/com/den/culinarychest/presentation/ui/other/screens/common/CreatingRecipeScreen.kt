package com.den.culinarychest.presentation.ui.other.screens.common

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.other.common.components.TextInput.NumberTextInput
import com.den.culinarychest.presentation.ui.other.common.components.TextInput.RecipeDetailsTextInput
import com.den.culinarychest.presentation.ui.other.common.components.TextInput.SmallTextInput
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatingRecipeViewModel
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime

@Composable
fun CreatingRecipeScreen(
    navController: NavController,
    creatingRecipeViewModel: CreatingRecipeViewModel,
) {
    CreatingRecipe(
        navController = navController,
        creatingRecipeViewModel = creatingRecipeViewModel,
    )
}


@SuppressLint("NewApi")
@Composable
private fun CreatingRecipe(
    navController: NavController,
    creatingRecipeViewModel: CreatingRecipeViewModel,
) {

    var textTitle by remember { mutableStateOf("") }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var textIngredient by remember { mutableStateOf("") }
    var textRecipeStep by remember { mutableStateOf("") }
    var numberTextRecipeStep by remember { mutableStateOf("") }
    var textPreparationTime by remember { mutableStateOf("") }
    var countRecipeSteps by remember { mutableIntStateOf(1) }

    val steps = remember { mutableStateListOf<String>() }

    fun addStep(textRecipeStep: String, numberTextRecipeStep: String) {
        val newStep = "{\"Description\": \"$textRecipeStep\", \"Order\": \"$numberTextRecipeStep\"}"
        steps.add(newStep)
    }


    val titleValidation by remember {
        derivedStateOf { !textTitle.matches(Regex("^[а-яА-Я]+$")) }
    }

    val imageValidation by remember {
        derivedStateOf { imageFile != null }
    }
    val ingredientsValidation by remember {
        derivedStateOf { !textIngredient.matches(Regex("^[а-яА-Я]+$")) }
    }

    val preparationTimeValidation by remember {
        derivedStateOf { !textIngredient.matches(Regex("^[0-9]+$")) }
    }

    var clickButton by remember {
        mutableStateOf(false)
    }

    Column {
        TopBar(navController = navController)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            item {
                AddRecipePhoto { file ->
                    imageFile = file
                }
                RecipeInputs(
                    onTitleTextChanged = { textTitle = it },
                    onIngredientsTextChanged = { textIngredient = it },
                    onTimeTextChanged = { textPreparationTime = it },
                    clickButton = clickButton,
                    titleValidation = titleValidation,
                    ingredientsValidation = ingredientsValidation,
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
                        outputTextHint = "$itemNumber",
                        onTextChanged = { newTextRecipeStep -> textRecipeStep = newTextRecipeStep },
                        onNumberTextChanged = { newNumberTextRecipeStep ->
                            numberTextRecipeStep = newNumberTextRecipeStep
                        },
                        onTextValidation = { it.matches(Regex("[а-яА-Я0-9]+")) },
                        onEnterPressed = { addStep(textRecipeStep, numberTextRecipeStep) }
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp, bottom = 16.dp)
                        .padding(horizontal = 80.dp)
                ) {
                    if (textTitle.isNotBlank() && imageFile != null && textIngredient.isNotBlank() && steps.toList() != null && LocalDateTime.now().toString().isNotBlank()) {
                        CreatingRecipeSaveButton(
                            buttonText = stringResource(id = R.string.save_recipe_text),
                            colorButtonText = SoftGray,
                            buttonColor = SoftOrange,
                            onClick = {
                                clickButton = true
                                if (titleValidation == false && ingredientsValidation == false && imageValidation == true && preparationTimeValidation == true && steps.isNotEmpty()) {
                                    creatingRecipeViewModel.createRecipeUser(
                                        recipeImage = imageFile!!,
                                        title = textTitle,
                                        ingredients = textIngredient,
                                        steps = steps.toList(),
                                        creationDate = LocalDateTime.now().toString(),
                                        preparationTime = textPreparationTime
                                    )
                                }
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun TopBar(navController: NavController) {
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
private fun AddRecipePhoto(
    addImage: (File) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
            uri?.let {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                selectedImageBitmap = bitmap
                // Сохранение изображения в файл
                val file =
                    File(context.cacheDir, uri.path.toString().substringAfter("document/"))
                file.outputStream().use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
                addImage(file)
            }
        }

    if (selectedImageBitmap == null) {
        Image(
            painter = painterResource(id = R.drawable.add_recipe_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .clickable { imagePickerLauncher.launch("image/*") }
        )
    } else {
        selectedImageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp))
                    .alpha(.7f)
                    .clickable { imagePickerLauncher.launch("image/*") }
                    .clip(shape = RoundedCornerShape(15.dp))
            )
        }
    }
}


@Composable
private fun RecipeInputs(
    onTitleTextChanged: (String) -> Unit,
    onIngredientsTextChanged: (String) -> Unit,
    onTimeTextChanged: (String) -> Unit,
    clickButton: Boolean,
    titleValidation: Boolean,
    ingredientsValidation: Boolean,
) {

    var preparationTimeText by remember { mutableStateOf("") }
    onTimeTextChanged(preparationTimeText)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        RecipeDetailsTextInput(
            hintOutput = stringResource(R.string.enter_title_recipe),
            errorText = "Проверьте правильность введенного названия рецепта",
            validationEnteredText = if (clickButton) titleValidation else false,
            enteredText = onTitleTextChanged,
        )
        Spacer(modifier = Modifier.height(10.dp))
        RecipeDetailsTextInput(
            hintOutput = stringResource(R.string.enter_ingredients_recipe),
            errorText = "Проверьте правильность введенных ингредиентов рецепта",
            validationEnteredText = if (clickButton) ingredientsValidation else false,
            enteredText = onIngredientsTextChanged,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.enter_time_recipe),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = if (clickButton && preparationTimeText.isEmpty()) Color.Red else LightGray
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(end = 4.dp)
            )
            SmallTextInput(
                hintOutput = stringResource(R.string.in_minutes_recipe),
                validationEnteredText = { it.matches(Regex("[0-9]+")) },
                enteredText = { text -> preparationTimeText = text }
            )
        }
    }
}

@Composable
private fun DescribeStepsRecipe(
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
    buttonText: String,
    colorButtonText: Color,
    buttonColor: Color,
    onClick: () -> Unit
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
                onClick()
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







