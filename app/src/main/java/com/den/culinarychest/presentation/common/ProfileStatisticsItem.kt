package com.den.culinarychest.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray

@Composable
fun ProfileStatisticsItem(
    textStatistic: String,
    numberStatistic: String
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = textStatistic,
            style = TextStyle(
                color = SoftGray,
                fontSize = 12.sp
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = numberStatistic,
            style = TextStyle(
                color = SoftGray,
                fontSize = 14.sp
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}