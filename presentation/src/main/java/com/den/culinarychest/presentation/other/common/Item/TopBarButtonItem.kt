package com.den.culinarychest.presentation.other.common.Item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.other.ui.theme.SoftGray
import com.den.culinarychest.presentation.other.ui.theme.SoftOrange
import kotlinx.coroutines.Job

@Composable
fun TopBarButtonItem(
    isSelected: Boolean,
    textButton: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 159.dp, height = 46.dp)
            .background(color = SoftOrange, shape = RoundedCornerShape(size = 12.dp))
            .border(
                width = if (isSelected) 0.50.dp else 0.15.dp,
                color = SoftGray,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                color = if (isSelected) Color.Black else SoftGray,
                fontSize = 14.sp,
            )
        )
    }
}