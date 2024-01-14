package com.den.culinarychest.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun PushButton(
    textButton: String,
    transitionalText: String,
    navController: NavController,
    navigationButton: String,
    navigationText: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 76.dp)
            .height(46.dp)
            .border(width = 0.3.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(SoftOrange),
        onClick = { navController.navigate(navigationButton) }
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            )
        )
    }
    Spacer(modifier = Modifier.height(height = 6.dp))
    Text(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            navController.navigate(navigationText)
        },
        text = transitionalText,
        style = TextStyle(
            fontSize = 14.sp,
            color = SoftGray
        )
    )
}