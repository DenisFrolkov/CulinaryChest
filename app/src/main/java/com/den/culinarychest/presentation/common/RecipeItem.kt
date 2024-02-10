package com.den.culinarychest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R
import com.den.culinarychest.presentation.route.AppNavigationRoute

@Composable
fun RecipeItem(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clickable {
                navController.navigate(AppNavigationRoute.RecipeScreen.route)
            }
            .border(width = .15.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .background(SoftOrange, RoundedCornerShape(12.dp))
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 0.dp, bottom = 0.dp)
                    .size(size = 94.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp, top = 12.dp)
            ) {
                Text(
                    text = "Макароны с крабовыми палочками, сметаной и чесноком",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = SoftGray
                    )
                )
                Text(
                    text = "Ингредиенты: макароны, крабовые палочки, чеснок, масло сливочное, сыр твёрдый, сметана, мука... ",
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = SoftGray
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        Row() {
            Row() {
                RecipeInformationItems(
                    iconPainter = painterResource(id = R.drawable.star),
                    sizeIcon = 16,
                    textInformation = "4.5",
                    paddingStart = 23,
                    paddingTop = 7,
                    paddingEnd = 0,
                    paddingBottom = 7,
                    fontSize = 10
                )
            }
            Row() {
                RecipeInformationItems(
                    iconPainter = painterResource(id = R.drawable.time),
                    sizeIcon = 16,
                    textInformation = "30 мин",
                    paddingStart = 14,
                    paddingTop = 7,
                    paddingEnd = 0,
                    paddingBottom = 7,
                    fontSize = 10
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                RecipeInformationItems(
                    iconPainter = painterResource(id = R.drawable.calendar),
                    sizeIcon = 16,
                    textInformation = "23.10.2020",
                    paddingStart = 0,
                    paddingTop = 7,
                    paddingEnd = 0,
                    paddingBottom = 7,
                    fontSize = 10
                )
            }
        }
    }
}