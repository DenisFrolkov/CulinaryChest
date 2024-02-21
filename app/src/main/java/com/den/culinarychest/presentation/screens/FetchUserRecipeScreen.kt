package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.DisplayRecipeInfo
import com.den.culinarychest.presentation.common.Item.StepRecipeItem
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun FetchUserRecipeScreen(
    navController: NavController
) {
    FetchUserRecipe(controller = navController)
}

@Composable
fun FetchUserRecipe(
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
    Column {
        FetchUserRecipeTopBar(
            controller = controller,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            item {
                FetchUserRecipeImage()
                FetchUserRecipeMiniInformation()
                FetchUserRecipeTitle()
                FetchUserRecipeIngredient(recipeIngredients = recipeIngredients)
                FetchUserRecipeSteps(recipeSteps = recipeSteps)
            }
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FetchUserRecipeTopBar(
    controller: NavController,
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
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {  },
            painter = painterResource(id = R.drawable.recipe_paraments_icon),
            contentDescription = null
        )
    }
}

@Composable
fun FetchUserRecipeImage(  ) {
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp)),
        painter = painterResource(id = R.drawable.image_recipe),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FetchUserRecipeMiniInformation(
    
) {
    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 6.dp, end = 8.dp)
    ) {
        DisplayRecipeInfo(
            iconRecipeInfo = painterResource(id = R.drawable.recipe_info_star_icon),
            sizeRecipeInfoIcon = 24,
            textRecipeInfo = "4.5",
            textFontSize = 16
        )
        Spacer(modifier = Modifier.width(8.dp))
        DisplayRecipeInfo(
            iconRecipeInfo = painterResource(id = R.drawable.recipe_info_time_icon),
            sizeRecipeInfoIcon = 24,
            textRecipeInfo = "30 мин",
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
                textRecipeInfo = "23.10.2020",
                textFontSize = 12
            )
        }
    }
}

@Composable
fun FetchUserRecipeTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        text = "Макароны с крабовыми палочками, сметаной и чесноком",
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
fun FetchUserRecipeSteps(
    recipeSteps: Array<String>
) {
    Text(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 6.dp),
        text = stringResource(R.string.preparation_steps_text),
        style = TextStyle(
            fontSize = 16.sp,
            color = SoftGray
        )
    )
    repeat(recipeSteps.size) { number ->
        val elementNumber = number + 1
        StepRecipeItem(
            numberStep = "$elementNumber",
            textStep = recipeSteps[number]
        )
    }
}