package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Item.ProfileStatisticsItem
import com.den.culinarychest.presentation.common.Button.SettingButton
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SoftOrange)
                .border(width = 0.1.dp, color = SoftGray)
        ) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(size = 100.dp)
                            .background(color = SoftGray, shape = RoundedCornerShape(size = 50.dp))
                    ) { }
                    Text(
                        text = "Denis123112",
                        style = TextStyle(
                            color = SoftGray,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp).padding(horizontal = 18.dp)
                ) {
                    ProfileStatisticsItem(
                        textStatistic = stringResource(R.string.favorite_recipe_text),
                        numberStatistic = "12"
                    )
                    ProfileStatisticsItem(
                        textStatistic = stringResource(R.string.created_recipe_text),
                        numberStatistic = "3"
                    )
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
                    .padding(horizontal = 88.dp)
            ) {
                SettingButton(
                    borderColor = SoftGray,
                    textButton = stringResource(R.string.edit_text),
                    textColor = SoftGray
                )
                SettingButton(
                    borderColor = Color.Red,
                    textButton = stringResource(R.string.exit_text),
                    textColor = Color.Red
                )
            }
        }
    }
}

