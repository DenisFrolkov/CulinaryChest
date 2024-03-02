package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Button.SaveButton
import com.den.culinarychest.presentation.ui.theme.EditRecipeColor
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.LightRed
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun EditRecipeScreen(
    navController: NavController
) {
    EditRecipe(
//        controller = navController
    )
}

@Preview
@Composable
fun EditRecipe(
//    controller: NavController
) {
    var timeRecipeText by remember { mutableStateOf("30") }

    var showEditRecipeMenu by remember { mutableStateOf(false) }

    Column {
        EditRecipeTopBar(
//            controller = controller
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
                .padding(horizontal = 10.dp)
        ) {
            item {
                EditRecipeImage()
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeTime(
                    timeRecipeText = timeRecipeText,
                    newTimeRecipeText = { newTimeRecipeText -> timeRecipeText = newTimeRecipeText }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeTitle(
                    clickShowEditRecipeMenu = { newValueShowEditRecipeMenu ->
                        showEditRecipeMenu = newValueShowEditRecipeMenu
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeIngredients(
                    clickShowEditRecipeMenu = { newValueShowEditRecipeMenu ->
                        showEditRecipeMenu = newValueShowEditRecipeMenu
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
                EditRecipeSteps(
                    clickShowEditRecipeMenu = { newValueShowEditRecipeMenu ->
                        showEditRecipeMenu = newValueShowEditRecipeMenu
                    }
                )
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .padding(horizontal = 80.dp)
                ) {
                    SaveButton(
                        buttonText = stringResource(id = R.string.save_recipe_changes),
                        colorButtonText = Color.Black,
                        buttonColor = EditRecipeColor
                    )
                }
            }
        }
        EditRecipeMenu(
            showDialog = showEditRecipeMenu,
            onDismiss = { newValueShowEditRecipeMenu ->
                showEditRecipeMenu = newValueShowEditRecipeMenu
            }
        )
    }
}

@Composable
fun EditRecipeTopBar(
//    controller: NavController
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
//                    controller.popBackStack()
                }
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun EditRecipeImage() {
    Box(
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_recipe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp))
                .alpha(.7f)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { }
        )
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
                .clip(shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .clickable { }
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
fun EditRecipeTime(
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
                Divider(
                    thickness = 0.8.dp,
                    color = Color.Black,
                    modifier = Modifier.width(30.dp)
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
fun EditRecipeTitle(
    clickShowEditRecipeMenu: (Boolean) -> Unit
) {

    val recipeTitleText = "Макароны с крабовыми палочками, сметаной и чесноком"

    val MAX_LENGTH = 67

    val truncateText = if (recipeTitleText.length > MAX_LENGTH) {
        recipeTitleText.take(MAX_LENGTH) + "…"
    } else {
        recipeTitleText
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
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
fun EditRecipeIngredients(
    clickShowEditRecipeMenu: (Boolean) -> Unit
) {

    val ingredientsRecipeText = """
        Макароны – 100 г, Крабовые палочки – 100 г, Чеснок – 1 зубчик, Масло сливочное – 10 г, Сыр твёрдый – 10 г, Сметана – 2 ст. ложки, Мука – 1/4 ч. ложки, Травы прованские сушеные – 1/2 ч. ложки, Соль – по вкусу, Перец чёрный молотый – по вкусу;
    """.trimIndent()

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
fun EditRecipeSteps(
    clickShowEditRecipeMenu: (Boolean) -> Unit
) {
    val recipeStepsText = arrayOf(
        "Подготавливаем все необходимые продукты.",
        "Начинаем с приготовления макарон. В небольшую кастрюлю наливаем воду, добавляем 1 щепотку соли, доводим до кипения. Опускаем макароны в кипящую воду, перемешиваем и варим, периодически помешивая, примерно 8-10 минут или согласно инструкции на упаковке, до мягкости. Отваренные макароны откидываем на дуршлаг, даём стечь лишней жидкости.",
        "С крабовых палочек снимаем упаковку. Нарезаем крабовые палочки кружочками. Чеснок очищаем и нарезаем мелкими кусочками."
    )

    Column {
        Spacer(modifier = Modifier.height(height = 16.dp))
        Text(
            text = stringResource(id = R.string.preparation_steps_text),
            style = TextStyle(
                fontSize = 18.sp,
                color = SoftGray
            )
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        repeat(recipeStepsText.size) { number ->
            val elementNumber = number + 1
            EditRecipeStepItem(
                numberStep = "$elementNumber",
                textStep = recipeStepsText[number],
                clickShowEditRecipeMenu = { newValueShowEditRecipeMenu ->
                    clickShowEditRecipeMenu(newValueShowEditRecipeMenu)
                }
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
        }
    }
}

@Composable
fun EditRecipeStepItem(
    numberStep: String,
    textStep: String,
    clickShowEditRecipeMenu: (Boolean) -> Unit
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
                clickShowEditRecipeMenu(true)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "$numberStep.",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = SoftGray
                ),
                modifier = Modifier.padding(start = 5.dp, top = 5.dp)
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
fun EditRecipeMenu(
    showDialog: Boolean,
    onDismiss: (Boolean) -> Unit
) {
    var editText by remember { mutableStateOf("") }

    var isHintVisible by remember { mutableStateOf(true) }

    if (showDialog) {
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
                        text = "Редактирование названия рецепта",
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