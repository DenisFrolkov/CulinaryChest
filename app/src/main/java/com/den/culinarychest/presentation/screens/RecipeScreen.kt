package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.den.culinarychest.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.common.RecipeInformationItems
import com.den.culinarychest.presentation.common.StepRecipeItem
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Preview
@Composable
fun RecipeScreen() {
    Recipe()
}

@Composable
fun Recipe() {

    val stringResources = LocalContext.current.resources

    var clickLike by remember { mutableStateOf(false) }
    val text = """
        Макароны – 100 г, Крабовые палочки – 100 г, Чеснок – 1 зубчик, Масло сливочное – 10 г, Сыр твёрдый – 10 г, Сметана – 2 ст. ложки, Мука – 1/4 ч. ложки, Травы прованские сушеные – 1/2 ч. ложки, Соль – по вкусу, Перец чёрный молотый – по вкусу;
    """.trimIndent()
    val recipeElements = arrayOf(
        "Подготавливаем все необходимые продукты.",
        "Начинаем с приготовления макарон. В небольшую кастрюлю наливаем воду, добавляем 1 щепотку соли, доводим до кипения. Опускаем макароны в кипящую воду, перемешиваем и варим, периодически помешивая, примерно 8-10 минут или согласно инструкции на упаковке, до мягкости. Отваренные макароны откидываем на дуршлаг, даём стечь лишней жидкости.",
        "С крабовых палочек снимаем упаковку. Нарезаем крабовые палочки кружочками. Чеснок очищаем и нарезаем мелкими кусочками."
    )
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SoftOrange)
                .border(width = 0.1.dp, color = SoftGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = null
                )
                if (clickLike == false) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { clickLike = true },
                        painter = painterResource(id = R.drawable.heart_icon_dont_like),
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { clickLike = false },
                        painter = painterResource(id = R.drawable.heart_icon_like),
                        contentDescription = null
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .border(width = 0.dp, color = SoftPink, shape = RoundedCornerShape(12.dp)),
                    painter = painterResource(id = R.drawable.image_recipe),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Row() {
                    RecipeInformationItems(
                        iconPainter = painterResource(id = R.drawable.star),
                        sizeIcon = 24,
                        textInformation = "4.5",
                        paddingStart = 18,
                        paddingTop = 8,
                        paddingEnd = 0,
                        paddingBottom = 8,
                        fontSize = 14
                    )
                    RecipeInformationItems(
                        iconPainter = painterResource(id = R.drawable.time),
                        sizeIcon = 24,
                        textInformation = "30 мин",
                        paddingStart = 10,
                        paddingTop = 8,
                        paddingEnd = 0,
                        paddingBottom = 8,
                        fontSize = 14
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 18.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RecipeInformationItems(
                            iconPainter = painterResource(id = R.drawable.calendar),
                            sizeIcon = 24,
                            textInformation = "23.10.2020",
                            paddingStart = 0,
                            paddingTop = 8,
                            paddingEnd = 0,
                            paddingBottom = 8,
                            fontSize = 14
                        )
                    }
                }
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
                        .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
                ) {
                    Text(
                        modifier = Modifier.padding(all = 6.dp),
                        text = stringResources.getString(R.string.ingredients_text),
                        style = TextStyle(
                            fontSize = 12.sp,
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
                                    color = SoftGray
                                )
                            ) {
                                append(text.replace(", ", "\n"))
                            }
                        }
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 6.dp),
                    text = stringResources.getString(R.string.preparation_steps_text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = SoftGray
                    )
                )
                repeat(recipeElements.size) { number ->
                    val elementNumber = number + 1
                    StepRecipeItem(
                        numberStep = "$elementNumber",
                        textStep = recipeElements[number]
                    )
                }
            }
        }
    }
}

