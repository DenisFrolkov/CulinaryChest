package com.den.culinarychest.presentation.common.Item

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
fun DisplayRecipeInfo(
    iconRecipeInfo: Painter,
    sizeRecipeInfoIcon: Int,
    textRecipeInfo: String,
    textFontSize: Int
) {
    Row {
        Image(
            painter = iconRecipeInfo,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(vertical = 8.dp )
                .size(size = sizeRecipeInfoIcon.dp)
        )
        Text(
            text = textRecipeInfo,
            style = TextStyle(
                fontSize = textFontSize.sp,
                color = SoftGray
            ),
            modifier = Modifier
                .padding(start = 4.dp)
                .align(Alignment.CenterVertically)
        )
    }
}
