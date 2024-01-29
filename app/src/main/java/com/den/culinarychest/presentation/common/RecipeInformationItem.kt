package com.den.culinarychest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray

@Composable
fun RecipeInformationItems(
    iconPainter: Painter,
    sizeIcon: Int,
    textInformation: String,
    paddingStart: Int,
    paddingTop: Int,
    paddingEnd: Int,
    paddingBottom: Int,
    fontSize: Int
    ) {
    Row() {
        Image(
            modifier = Modifier
                .padding(start = paddingStart.dp, top = paddingTop.dp, end = paddingEnd.dp, bottom = paddingBottom.dp)
                .size(size = sizeIcon.dp),
            painter = iconPainter,
            contentDescription = "Тут должны быть часы ",
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically),
            text = textInformation,
            style = TextStyle(
                fontSize = fontSize.sp,
                color = SoftGray
            )
        )
    }
}
