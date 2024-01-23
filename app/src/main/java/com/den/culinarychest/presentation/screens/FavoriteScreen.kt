package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun FavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 82.dp),
            text = "Здесь еще ничего нет :(",
            style = TextStyle(
                color = SoftGray,
                fontSize = 16.sp
            )
        )
    }
}