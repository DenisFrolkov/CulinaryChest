package com.den.culinarychest.presentation.common.Item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun RecipeItem(
    controller: NavController,
    textRouteNavigation: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable {
                controller.navigate(textRouteNavigation)
            }
            .border(width = .15.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
            .background(SoftOrange, RoundedCornerShape(12.dp))
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.recipe_space_image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
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
                        fontSize = 12.sp,
                        color = SoftGray
                    ),
                    modifier = Modifier.padding(top = 4.dp),
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 23.dp, top = 6.dp, end = 8.dp, bottom = 4.dp)
        ) {
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_star_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = "4.5",
                textFontSize = 12
            )
            Spacer(modifier = Modifier.width(8.dp))
            DisplayRecipeInfo(
                iconRecipeInfo = painterResource(id = R.drawable.recipe_info_time_icon),
                sizeRecipeInfoIcon = 20,
                textRecipeInfo = "30 мин",
                textFontSize = 12
            )
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DisplayRecipeInfo(
                    iconRecipeInfo = painterResource(id = R.drawable.recipe_info_calendar_icon),
                    sizeRecipeInfoIcon = 16,
                    textRecipeInfo = "23.10.2020",
                    textFontSize = 10
                )
            }
        }
    }
}