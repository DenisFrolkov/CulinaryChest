package com.den.culinarychest.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun SettingButton(
    borderColor: Color,
    textButton: String,
    textColor: Color
) {
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 88.dp)
            .height(height = 40.dp)
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.5.dp, color = borderColor, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontSize = 14.sp,
                color = textColor
            )
        )
    }
}