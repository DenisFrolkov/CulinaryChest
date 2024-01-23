package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.ProfileStatisticsItem
import com.den.culinarychest.presentation.common.SettingButton
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Preview
@Composable
fun ProfileScreen() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SoftOrange)
                .border(width = 0.1.dp, color = SoftGray)
        ) {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(size = 100.dp)
                            .background(color = SoftGray, shape = RoundedCornerShape(size = 50.dp))
                    ) { }
                    Text(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .align(Alignment.CenterHorizontally),
                        text = "Denis123112",
                        style = TextStyle(
                            color = SoftGray,
                            fontSize = 12.sp
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, top = 12.dp, end = 18.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProfileStatisticsItem(TextStatistic = "Избранные рецепты", NumberStatistic = "12")
                    ProfileStatisticsItem(TextStatistic = "Созданные рецепты", NumberStatistic = "3")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SoftPink)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                SettingButton(borderColor = SoftGray, textButton = "Редактировать", textColor = SoftGray)
                SettingButton(borderColor = Color.Red, textButton = "Выйти", textColor = Color.Red)
            }
        }
    }
}

