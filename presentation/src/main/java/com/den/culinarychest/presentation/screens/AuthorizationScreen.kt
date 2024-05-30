package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Button.PushButton
import com.den.culinarychest.presentation.common.TextInput.AccountTextInput
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.application_user.Login
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthorizationScreen(
    navController: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {

    Authorization(
        controller = navController,
        applicationUserViewModel = applicationUserViewModel,
        tokenManager = tokenManager
    )
}


@Composable
fun Authorization(
    controller: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {

    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val tokenVerification by remember {
        derivedStateOf { tokenManager.getToken() == null }
    }

    val loginValidation by remember {
        derivedStateOf { login.length < 3 && login.isNotBlank() }
    }
    val passwordValidation by remember {
        derivedStateOf { password.length < 12 && password.isNotBlank() }
    }

    var clickButton by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.authorization_text),
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            ),
            modifier = Modifier.padding(bottom = 60.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            AccountTextInput(
                hintOutput = "Введите логин",
                errorText = "Проверьте правильность введенного логина",
                validationEnteredText = if (clickButton) tokenVerification else loginValidation,
                enteredText = { enteredText -> login = enteredText },
            )
            Spacer(modifier = Modifier.height(16.dp))
            AccountTextInput(
                hintOutput = "Введите пароль",
                errorText = "Проверьте правильность введенного пароля",
                validationEnteredText = if (clickButton) tokenVerification else passwordValidation,
                enteredText = { enteredText -> password = enteredText },
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        if (isLoading) {
            CircularProgressIndicator(
                color = SoftGray,
                strokeWidth = 1.5.dp
            )
        } else {
            PushButton(
                onClick = {
                    applicationUserViewModel.authorizeUser(
                        Login(userName = login, password = password)
                    )
                    coroutineScope.launch {
                        isLoading = true
                        delay(1000)
                        if (tokenManager.getToken() == null) {
                            isLoading = false
                            clickButton = true
                        } else {
                            clickButton = false
                            controller.navigate(AppNavigationRoute.BottomAppNavigationBar.route)
                        }
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                controller.navigate(AppNavigationRoute.RegistrationScreen.route)
            },
            text = stringResource(R.string.new_account_text),
            style = TextStyle(
                fontSize = 14.sp,
                color = SoftGray
            )
        )
    }
}
