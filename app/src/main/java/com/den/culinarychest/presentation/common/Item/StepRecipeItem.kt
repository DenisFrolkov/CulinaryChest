package com.den.culinarychest.presentation.common.Item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun StepRecipeItem(
    numberStep: String,
    textStep: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
    ) {
        Text(
            modifier = Modifier.padding(all = 6.dp),
            text = "$numberStep.",
            style = TextStyle(
                fontSize = 12.sp,
                color = SoftGray
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 14.dp, end = 6.dp, bottom = 6.dp),
            text = textStep,
            style = TextStyle(
                fontSize = 16.sp,
                color = SoftGray
            )
        )
    }
}