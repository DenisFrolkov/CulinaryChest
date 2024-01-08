package com.den.culinarychest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R

@Preview
@Composable
fun RecipeItem(
////    recipeImage: Painter,
//    titleText: String,
//    ingredientText: String
) {
    Row(modifier = Modifier
        .background(SoftOrange)
        .padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp),
            painter = painterResource(id = R.drawable.image),
            contentDescription = "Тут должна быть картинка рецепта ;)",
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = "Макароны с крабовыми палочками, сметаной и чесноком",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = SoftGray
                )
            )
            Text(
                text = "Ингридиенты",
                style = TextStyle(
                    fontSize = 10.sp,
                    color = SoftGray
                )
            )
        }
    }
}