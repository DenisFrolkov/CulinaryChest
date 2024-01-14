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
import com.den.culinarychest.presentation.navigation.NavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun RegistrationScreen(
    navController: NavController
) {
    Registration(
        navController = navController
    )
}

@Composable
fun Registration(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(height = 112.dp))
        Text(
            text = "Регистрация",
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            )
        )
        Spacer(modifier = Modifier.height(height = 24.dp))
        TextInput(inputText = "логин")
        Spacer(modifier = Modifier.height(height = 34.dp))
        TextInput(inputText = "почту")
        Spacer(modifier = Modifier.height(height = 34.dp))
        TextInput(inputText = "пароль")
        Spacer(modifier = Modifier.height(height = 34.dp))
        TextInput(inputText = "пароль")
        Spacer(modifier = Modifier.height(height = 52.dp))
        PushButton(textButton = "Создать аккаунт", transitionalText = "Войти в существующий?", navController = navController, navigationButton = NavigationRoute.BottomNavigationBar.route, navigationText = NavigationRoute.AuthorizationScreen.route)
    }
}