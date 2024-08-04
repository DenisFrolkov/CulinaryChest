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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.main.viewmodel.ImageViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageRecipeUserViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageStepsViewModel
import com.den.culinarychest.presentation.ui.other.common.model.AddStep
import com.den.culinarychest.presentation.ui.other.common.model.AddStepCreate
import com.den.culinarychest.presentation.ui.other.common.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.EditRecipeColor
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.LightRed
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.example.culinarychest.domain.model.recipe.Recipe
import java.io.File
import java.io.InputStream

@Composable
fun EditRecipeScreen(
    navController: NavController,
    imageViewModel: ImageViewModel,
    manageRecipeUserViewModel: ManageRecipeUserViewModel,
    manageStepsViewModel: ManageStepsViewModel,
    recipe: Recipe,
) {
    EditRecipe(
        controller = navController,
        imageViewModel = imageViewModel,
        manageRecipeUserViewModel = manageRecipeUserViewModel,
        manageStepsViewModel = manageStepsViewModel,
        recipe = recipe,
    )
}

@Composable
private fun EditRecipe(
    controller: NavController,
    imageViewModel: ImageViewModel,
    manageRecipeUserViewModel: ManageRecipeUserViewModel,
    manageStepsViewModel: ManageStepsViewModel,
    recipe: Recipe,
) {

    val recipeImageUrl = imageViewModel.photoUrl.collectAsState().value

    val stepsFromServer = remember { mutableStateListOf<AddStep>() }
    val stepsCreateApp = remember { mutableStateListOf<AddStepCreate>() }

    fun addStepFromServer(stepId: String, textRecipeStep: String, numberTextRecipeStep: String) {
        val newStep =
            AddStep(
                stepId,
                textRecipeStep,
                numberTextRecipeStep
            )
        stepsFromServer.add(newStep)
    }

    fun addStepCreateApp(textRecipeStep: String, numberTextRecipeStep: String) {
        val isStepAlreadyExists =
            stepsCreateApp.any { it.textRecipeStep == textRecipeStep && it.numberTextRecipeStep == numberTextRecipeStep }

        if (!isStepAlreadyExists) {
            val newStep =
                AddStepCreate(
                    textRecipeStep,
                    numberTextRecipeStep
                )
            stepsCreateApp.add(newStep)
        }
    }

    var stepsInitialized by remember { mutableStateOf(false) }

    if (!stepsInitialized) {
        recipe.steps.forEach {
            addStepFromServer(it.stepId.toString(), it.description, it.order.toString())
        }
        stepsInitialized = true
    }


    var titleEditRecipeMenu by remember { mutableStateOf("") }
    var imageFile by remember { mutableStateOf<File?>(null) }
    var textEditRecipeMenu by remember { mutableStateOf("") }
    var numberEditRecipeMenu by remember { mutableStateOf("") }
    var labelEditRecipeMenu by remember { mutableStateOf("") }
    var idStep by remember { mutableStateOf("") }
    var indexStepEditRecipeMenu by remember { mutableIntStateOf(0) }

    var titleRecipeText by remember { mutableStateOf(recipe.title) }
    var ingredientsRecipeText by remember { mutableStateOf(recipe.ingredients) }

    var timeRecipeText by remember { mutableStateOf(recipe.preparationTime) }
    val savedCountRecipeText by remember { mutableIntStateOf(recipe.savedCount) }

    var showEditRecipeMenu by remember { mutableStateOf(false) }
    var showEditRecipeMenuStep by remember { mutableStateOf(false) }


    fun updateStepFromServer(index: Int, stepFromServer: AddStep) {
        if (index in 0 until stepsFromServer.size) {
            stepsFromServer[index] = stepFromServer
        } else {
            println("Ошибка: Шаг с индексом $index не существует в списке")
        }
    }

    fun updateStepCreateApp(index: Int, stepCreateApp: AddStepCreate) {
        if (index in 0 until stepsCreateApp.size) {
            stepsCreateApp[index] = stepCreateApp
        } else {
            println("Ошибка: Шаг с индексом $index не существует в списке")
        }
    }


    var nextStepOrder by remember { mutableStateOf(recipe.steps.size) }

    if (labelEditRecipeMenu == "title") {
        titleRecipeText = textEditRecipeMenu
    } else if (labelEditRecipeMenu == "ingredients") {
        ingredientsRecipeText = textEditRecipeMenu
    } else if (labelEditRecipeMenu == "createStep") {
        if (textEditRecipeMenu != "") {
            addStepCreateApp(
                textRecipeStep = textEditRecipeMenu,
                numberTextRecipeStep = numberEditRecipeMenu
            )
        }
    } else if (labelEditRecipeMenu == "updateStepCreateApp") {
        updateStepCreateApp(
            index = indexStepEditRecipeMenu,
            stepCreateApp = AddStepCreate(
                textEditRecipeMenu,
                numberEditRecipeMenu
            )
        )
    } else if (labelEditRecipeMenu == "updateStepFromServer") {
        updateStepFromServer(
            index = indexStepEditRecipeMenu,
            stepFromServer = AddStep(
                idStep,
                textEditRecipeMenu,
                numberEditRecipeMenu
            )
        )
    }

    Column {
        EditRecipeTopBar(
            controller = controller
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
                .padding(horizontal = 10.dp)
        ) {
            item {
                EditRecipeImage(
                    recipeImageUrl = recipeImageUrl,
                    addImage = { image -> imageFile = image }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeTime(
                    timeRecipeText = timeRecipeText,
                    newTimeRecipeText = { newTimeRecipeText -> timeRecipeText = newTimeRecipeText }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeTitle(
                    textTitle = titleRecipeText,
                    clickShowEditRecipeMenu = { newValueShowEditRecipeMenu ->
                        showEditRecipeMenu = newValueShowEditRecipeMenu
                    },
                    textEditRecipeMenu = { getTextEditRecipeMenu ->
                        textEditRecipeMenu = getTextEditRecipeMenu
                    },
                    passedEditRecipeMenuTitle = { newTitle -> titleEditRecipeMenu = newTitle },
                    passedEditRecipeMenuLabel = { label -> labelEditRecipeMenu = label }
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditRecipeIngredients(
                    textIngredients = ingredientsRecipeText,
                    textEditRecipeMenu = { newText -> textEditRecipeMenu = newText },
                    passedEditRecipeMenuTitle = { newTitle -> titleEditRecipeMenu = newTitle },
                    clickShowEditRecipeMenu = { newValue -> showEditRecipeMenu = newValue },
                    passedEditRecipeMenuLabel = { label -> labelEditRecipeMenu = label }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column {
                    Spacer(modifier = Modifier.height(height = 16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(top = 6.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.preparation_steps_text),
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = SoftGray
                            )
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
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        nextStepOrder++
                                        labelEditRecipeMenu = "createStep"
                                        titleEditRecipeMenu = "Создание шага №${nextStepOrder}"
                                        textEditRecipeMenu = ""
                                        numberEditRecipeMenu = "$nextStepOrder"
                                        showEditRecipeMenuStep = true
                                    }
                            )
                        }
                    }

                    stepsFromServer.forEachIndexed { index, step ->
                        EditRecipeStepItem(
                            stepId = step.stepId,
                            numberStep = step.numberTextRecipeStep,
                            indexStep = "$index",
                            textStep = step.textRecipeStep,
                            passStepId = { stepId ->
                                idStep = stepId
                            },
                            numberEditRecipeMenu = { newNumberText ->
                                numberEditRecipeMenu = newNumberText
                            },
                            textEditRecipeMenu = { newText -> textEditRecipeMenu = newText },
                            indexEditRecipeMenu = { indexStep ->
                                indexStepEditRecipeMenu = indexStep.toInt()
                            },
                            clickShowEditRecipeMenu = { newValue -> showEditRecipeMenu = newValue },
                            passedEditRecipeMenuTitle = { newTitleEditRecipeMenuText ->
                                titleEditRecipeMenu = newTitleEditRecipeMenuText
                            },
                            passedEditRecipeMenuLabel = {
                                labelEditRecipeMenu = "updateStepFromServer"
                            }
                        )
                        Spacer(modifier = Modifier.height(height = 10.dp))
                    }

                    stepsCreateApp.forEachIndexed { index, step ->
                        EditRecipeStepItem(
                            stepId = null,
                            numberStep = step.numberTextRecipeStep,
                            indexStep = "$index",
                            textStep = step.textRecipeStep,
                            passStepId = { stepId ->
                                idStep = stepId
                            },
                            numberEditRecipeMenu = { newNumberText ->
                                numberEditRecipeMenu = newNumberText
                            },
                            textEditRecipeMenu = { newText -> textEditRecipeMenu = newText },
                            indexEditRecipeMenu = { indexStep ->
                                indexStepEditRecipeMenu = indexStep.toInt()
                            },
                            clickShowEditRecipeMenu = { newValue -> showEditRecipeMenu = newValue },
                            passedEditRecipeMenuTitle = { newTitleEditRecipeMenuText ->
                                titleEditRecipeMenu = newTitleEditRecipeMenuText
                            },
                            passedEditRecipeMenuLabel = {
                                labelEditRecipeMenu = "updateStepCreateApp"
                            }
                        )
                        Spacer(modifier = Modifier.height(height = 10.dp))
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .padding(horizontal = 80.dp)
                ) {
                    SaveButton(
                        buttonText = stringResource(id = R.string.save_recipe_changes),
                        colorButtonText = Color.Black,
                        buttonColor = EditRecipeColor,
                        onClick = {
                            manageRecipeUserViewModel.updateRecipeUser(
                                recipe.recipeId,
                                titleRecipeText,
                                imageFile,
                                ingredientsRecipeText,
                                recipe.creationDate,
                                timeRecipeText,
                                savedCountRecipeText
                            )
                            stepsFromServer.toList().forEach { updateStep ->
                                manageStepsViewModel.updateStepRecipe(
                                    recipe.recipeId, step = AddStep(
                                        updateStep.stepId,
                                        updateStep.textRecipeStep,
                                        updateStep.numberTextRecipeStep
                                    )
                                )
                            }

                            stepsCreateApp.toList().forEach { createStep ->
                                manageStepsViewModel.createStepsRecipe(
                                    recipe.recipeId, step = AddStepCreate(
                                        createStep.textRecipeStep,
                                        createStep.numberTextRecipeStep
                                    )
                                )
                            }

                            controller.navigate(AppNavigationRoute.BottomAppNavigationBar.route)
                        }
                    )
                }
            }
        }
        EditRecipeMenu(
            titleEditRecipeMenu = titleEditRecipeMenu,
            textEditRecipeMenu = textEditRecipeMenu,
            labelEditRecipeMenu = labelEditRecipeMenu,
            showDialog = showEditRecipeMenu,
            onDismiss = { newValue -> showEditRecipeMenu = newValue },
            passNewText = { newText -> textEditRecipeMenu = newText },
            passLabelText = { label -> labelEditRecipeMenu = label }
        )

        EditRecipeMenuStep(
            titleEditRecipeMenu = titleEditRecipeMenu,
            textEditRecipeMenu = textEditRecipeMenu,
            labelEditRecipeMenu = labelEditRecipeMenu,
            indexStepEditRecipeMenu = indexStepEditRecipeMenu,
            passIndexStepText = { passIndexStep -> indexStepEditRecipeMenu = passIndexStep },
            numberEditRecipeMenu = numberEditRecipeMenu,
            passNumberText = { number -> numberEditRecipeMenu = number },
            showDialog = showEditRecipeMenuStep,
            onDismiss = { newValue -> showEditRecipeMenuStep = newValue },
            passNewText = { newText -> textEditRecipeMenu = newText },
            passLabelText = { label -> labelEditRecipeMenu = label },
            onClick = {
            }
        )
    }
}

@Composable
private fun EditRecipeTopBar(
    controller: NavController
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
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun EditRecipeImage(
    recipeImageUrl: String?,
    addImage: (File?) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 6.dp)
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
                    val file = File(context.cacheDir, "selectedImage.png")
                    file.outputStream().use { out ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                    addImage(file)
                }
            }

        if (selectedImageBitmap == null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipeImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(400.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(15.dp))
                    .alpha(.7f)
            )
            addImage(null)
        } else {
            selectedImageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(400.dp)
                        .clip(shape = RoundedCornerShape(15.dp))
                        .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(15.dp))
                        .alpha(.7f)
                        .clickable { imagePickerLauncher.launch("image/*") }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.9f)
                .background(
                    color = EditRecipeColor,
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .height(height = 98.dp)
                .align(Alignment.BottomCenter)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            Text(
                text = stringResource(id = R.string.click_change_image),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = SoftGray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun EditRecipeTime(
    timeRecipeText: String,
    newTimeRecipeText: (String) -> Unit
) {
    val validationTimeIcon =
        if (timeRecipeText.length > 3) painterResource(id = R.drawable.red_mistake_icon) else painterResource(
            id = R.drawable.edit_recipe_icon
        )
    val validationTimeColor = if (timeRecipeText.length > 3) LightRed else EditRecipeColor

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .weight(6f)
                .align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.enter_new_cooking_time),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = SoftGray
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Column {
                BasicTextField(
                    value = timeRecipeText,
                    onValueChange = { newTimeRecipeText(it) },
                    singleLine = true,
                    textStyle = TextStyle(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(24.dp)
                        .align(Alignment.CenterHorizontally)
                )
                HorizontalDivider(
                    modifier = Modifier.width(30.dp),
                    thickness = 0.8.dp,
                    color = Color.Black
                )
            }
        }
        Box(
            modifier = Modifier
                .background(color = validationTimeColor)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = validationTimeIcon,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .size(36.dp)
            )
        }
    }
}

@Composable
private fun EditRecipeTitle(
    textTitle: String,
    textEditRecipeMenu: (String) -> Unit,
    passedEditRecipeMenuTitle: (String) -> Unit,
    passedEditRecipeMenuLabel: (String) -> Unit,
    clickShowEditRecipeMenu: (Boolean) -> Unit,
) {
    val MAX_LENGTH = 67
    val title = textTitle

    val truncateText = if (title.length > MAX_LENGTH) {
        title.take(MAX_LENGTH) + "…"
    } else {
        title
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                textEditRecipeMenu(title)
                passedEditRecipeMenuLabel("title")
                passedEditRecipeMenuTitle("Редактирование названия рецепта")
                clickShowEditRecipeMenu(true)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = truncateText,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = SoftGray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
        Box(
            modifier = Modifier
                .background(color = EditRecipeColor)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_recipe_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 10.dp, vertical = 14.dp)
                    .size(36.dp)
            )
        }
    }
}

@Composable
private fun EditRecipeIngredients(
    textIngredients: String,
    clickShowEditRecipeMenu: (Boolean) -> Unit,
    textEditRecipeMenu: (String) -> Unit,
    passedEditRecipeMenuTitle: (String) -> Unit,
    passedEditRecipeMenuLabel: (String) -> Unit,
) {

    val ingredientsRecipeText = textIngredients.trimIndent()

    val MAX_LENGTH = 130

    val truncateText = if (ingredientsRecipeText.length > MAX_LENGTH) {
        ingredientsRecipeText.take(MAX_LENGTH) + "…"
    } else {
        ingredientsRecipeText
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                textEditRecipeMenu(ingredientsRecipeText)
                passedEditRecipeMenuLabel("ingredients")
                passedEditRecipeMenuTitle("Редактирование ингредиентов рецепта")
                clickShowEditRecipeMenu(true)
            }
    ) {
        Column(
            modifier = Modifier
                .weight(6f)
        ) {
            Text(
                text = stringResource(id = R.string.ingredients_text),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = SoftGray
                ),
                modifier = Modifier.padding(all = 10.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 15.sp,
                            color = SoftGray
                        )
                    ) {
                        append(truncateText.replace(", ", "\n"))
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .padding(bottom = 10.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = EditRecipeColor)
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_recipe_icon),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 88.dp)
                    .align(Alignment.Center)
                    .size(36.dp)
            )
        }
    }
}

@Composable
private fun EditRecipeStepItem(
    stepId: String?,
    numberStep: String,
    indexStep: String,
    textStep: String,
    passStepId: (String) -> Unit,
    numberEditRecipeMenu: (String) -> Unit,
    textEditRecipeMenu: (String) -> Unit,
    indexEditRecipeMenu: (String) -> Unit,
    clickShowEditRecipeMenu: (Boolean) -> Unit,
    passedEditRecipeMenuTitle: (String) -> Unit,
    passedEditRecipeMenuLabel: () -> Unit
) {
    val MAX_LENGTH = 67

    val truncateText = if (textStep.length > MAX_LENGTH) {
        textStep.take(MAX_LENGTH) + "…"
    } else {
        textStep
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                passStepId(stepId.toString())
                textEditRecipeMenu(textStep)
                indexEditRecipeMenu(indexStep)
                numberEditRecipeMenu(numberStep)
                passedEditRecipeMenuLabel()
                passedEditRecipeMenuTitle("Редактирование шага №$numberStep")
                clickShowEditRecipeMenu(true)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = numberStep,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = SoftGray,
                ),
                modifier = Modifier
                    .padding(start = 14.dp, top = 8.dp, bottom = 6.dp)
            )
            Text(
                text = truncateText,
                style = TextStyle(
                    fontSize = 15.sp,
                    color = SoftGray,
                ),
                modifier = Modifier
                    .padding(start = 14.dp, top = 8.dp, bottom = 6.dp)
            )

        }
        Box(
            modifier = Modifier
                .background(color = EditRecipeColor)
                .align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.edit_recipe_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .size(36.dp)
            )
        }
    }
}


@Composable
private fun EditRecipeMenu(
    titleEditRecipeMenu: String,
    textEditRecipeMenu: String,
    labelEditRecipeMenu: String,
    showDialog: Boolean,
    passNewText: (String) -> Unit,
    passLabelText: (String) -> Unit,
    onDismiss: (Boolean) -> Unit,
) {
    if (showDialog) {
        var editText by remember { mutableStateOf(textEditRecipeMenu) }
        val titleText by remember { mutableStateOf(titleEditRecipeMenu) }

        var isHintVisible by remember { mutableStateOf(editText.isEmpty()) }

        Dialog(onDismissRequest = { onDismiss(false) }) {
            Surface(
                modifier = Modifier
                    .padding(all = 18.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(color = SoftPink)
                ) {
                    Text(
                        text = titleText,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = SoftGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(vertical = 14.dp)
                    ) {
                        BasicTextField(
                            value = editText,
                            onValueChange = {
                                editText = it
                                isHintVisible = it.isEmpty()
                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            singleLine = false,
                            cursorBrush = SolidColor(Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp)
                        )
                        if (isHintVisible) {
                            Text(
                                text = "Введите новое название рецепта",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = LightGray
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 6.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = SoftOrange)
                            .clickable {
                                passNewText(editText)
                                passLabelText(labelEditRecipeMenu)
                                onDismiss(false)
                            }
                            .padding(all = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}

@Composable
private fun EditRecipeMenuStep(
    titleEditRecipeMenu: String,
    textEditRecipeMenu: String,
    numberEditRecipeMenu: String,
    indexStepEditRecipeMenu: Int,
    passIndexStepText: (Int) -> Unit,
    passNumberText: (String) -> Unit,
    labelEditRecipeMenu: String,
    showDialog: Boolean,
    passNewText: (String) -> Unit,
    passLabelText: (String) -> Unit,
    onDismiss: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    if (showDialog) {
        var editText by remember { mutableStateOf(textEditRecipeMenu) }
        val titleText by remember { mutableStateOf(titleEditRecipeMenu) }

        var isHintVisible by remember { mutableStateOf(editText.isEmpty()) }

        Dialog(onDismissRequest = { onDismiss(false) }) {
            Surface(
                modifier = Modifier
                    .padding(all = 18.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(color = SoftPink)
                ) {
                    Text(
                        text = titleText,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = SoftGray,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(vertical = 14.dp)
                    ) {
                        BasicTextField(
                            value = editText,
                            onValueChange = {
                                editText = it
                                isHintVisible = it.isEmpty()
                            },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            singleLine = false,
                            cursorBrush = SolidColor(Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp)
                        )
                        if (isHintVisible) {
                            Text(
                                text = "Введите новое название рецепта",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = LightGray
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 6.dp)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = SoftOrange)
                            .clickable {
                                passIndexStepText(indexStepEditRecipeMenu)
                                passNewText(editText)
                                passNumberText(numberEditRecipeMenu)
                                passLabelText(labelEditRecipeMenu)
                                onDismiss(false)
                                onClick()
                            }
                            .padding(all = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}

@Composable
private fun SaveButton(
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
