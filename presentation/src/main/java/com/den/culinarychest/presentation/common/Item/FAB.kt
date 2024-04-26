package com.den.culinarychest.presentation.common.Item

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun FAB(
    navController: NavController

) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(size = 25.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(size = 25.dp))
            .size(size = 50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(AppNavigationRoute.CreatingRecipeScreen.route)
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = null,
            modifier = Modifier
                .size(size = 20.dp)
        )
    }
}