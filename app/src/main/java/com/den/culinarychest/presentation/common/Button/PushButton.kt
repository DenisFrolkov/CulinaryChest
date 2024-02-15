package com.den.culinarychest.presentation.common.Button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun PushButton(
    textButton: String,
    fieldCheck: Boolean,
    controller: NavController,
    route: String,
    onButtonClick: (Boolean) -> Unit,
    fieldValidityCheck: Boolean
) {

    Box(
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.3.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                if (fieldValidityCheck) controller.navigate(route)
                if (fieldCheck) onButtonClick(true) else onButtonClick(false)
            },
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontSize = 20.sp,
                color = if (fieldCheck) SoftGray else LightGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 64.dp, vertical = 12.dp)
        )
    }
}