package com.den.culinarychest.presentation.common.Button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun PushButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.3.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
    ) {
        Text(
            text = "Войти",
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 64.dp, vertical = 12.dp)
        )
    }
}