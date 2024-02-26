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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.EditRecipeColor
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun EditRecipeScreen(
    navController: NavController
) {
    EditRecipe(controller = navController)
}

@Composable
fun EditRecipe(
    controller: NavController
) {

    val recipeIngredients = """
        Макароны – 100 г, Крабовые палочки – 100 г, Чеснок – 1 зубчик, Масло сливочное – 10 г, Сыр твёрдый – 10 г, Сметана – 2 ст. ложки, Мука – 1/4 ч. ложки, Травы прованские сушеные – 1/2 ч. ложки, Соль – по вкусу, Перец чёрный молотый – по вкусу;
    """.trimIndent()
    val recipeSteps = arrayOf(
        "Подготавливаем все необходимые продукты.",
        "Начинаем с приготовления макарон. В небольшую кастрюлю наливаем воду, добавляем 1 щепотку соли, доводим до кипения. Опускаем макароны в кипящую воду, перемешиваем и варим, периодически помешивая, примерно 8-10 минут или согласно инструкции на упаковке, до мягкости. Отваренные макароны откидываем на дуршлаг, даём стечь лишней жидкости.",
        "С крабовых палочек снимаем упаковку. Нарезаем крабовые палочки кружочками. Чеснок очищаем и нарезаем мелкими кусочками."
    )

    var timeRecipeText by remember { mutableStateOf("30") }

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
                EditImageRecipe()
                EditRecipeTime(
                    timeRecipeText = timeRecipeText,
                    newTimeRecipeText = { newTimeRecipeText -> timeRecipeText = newTimeRecipeText }
                )
//                FetchUserRecipeTitle()
//                FetchUserRecipeIngredient(recipeIngredients = recipeIngredients)
//                FetchUserRecipeSteps(recipeSteps = recipeSteps)
            }
        }
    }
}

@Composable
fun EditRecipeTopBar(
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
fun EditImageRecipe() {
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
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Введите новое время приготовления:",
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = SoftGray
                    ),
                    modifier = Modifier
                        .padding(vertical = 14.dp)
                        .padding(start = 16.dp, end = 12.dp)
                )
                Column() {
                    BasicTextField(
                        value = timeRecipeText,
                        onValueChange = { newTimeRecipeText(it) },
                        singleLine = true,
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
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .background(color = EditRecipeColor)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit_recipe_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.5.dp)
                        .size(36.dp)
                )
            }

        }
    }
}