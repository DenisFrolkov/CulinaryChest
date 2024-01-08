package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.presentation.common.PushButton
import com.den.culinarychest.presentation.common.TextInput
import com.den.culinarychest.presentation.navigaion.NavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun AuthorizationScreen(
    navController: NavController
) {
    Authorization(navController = navController)
}


@Composable
fun Authorization(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(height = 184.dp))
        Text(
            text = "Авторизация",
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            )
        )
        Spacer(modifier = Modifier.height(height = 60.dp))
        TextInput(inputText = "почту")
        Spacer(modifier = Modifier.height(height = 34.dp))
        TextInput(inputText = "пароль")
        Spacer(modifier = Modifier.height(height = 112.dp))
        PushButton(textButton = "Войти", transitionalText = "Создать аккаунт?", navController = navController, navigationButton = NavigationRoute.SearchScreen.route, navigationText = NavigationRoute.RegistrationScreen.route)
    }
}