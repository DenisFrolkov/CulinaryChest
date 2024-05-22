package com.den.culinarychest.presentation.screens

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.Button.PushButton
import com.den.culinarychest.presentation.common.TextInput.AccountTextInput
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    navController: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {
    Registration(
        controller = navController,
        applicationUserViewModel = applicationUserViewModel,
        tokenManager = tokenManager
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Registration(
    controller: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {

    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verificationPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val loginValidation by remember {
        derivedStateOf { login.length < 3 && login.isNotBlank() }
    }

    val emailValidation by remember {
        derivedStateOf { !Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotBlank() }
    }

    val passwordValidation by remember {
        derivedStateOf { password.length < 12 && password.isNotBlank() }
    }

    val verificationPasswordValidation by remember {
        derivedStateOf { password.isNotBlank() && verificationPassword != password }
    }

    var clickButton by remember {
        mutableStateOf(false)
    }

    val duplicationUserInfo by applicationUserViewModel.duplicationUserInfo.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(112.dp))
        Text(
            text = stringResource(R.string.registration_text),
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            )
        )
        duplicationUserInfo?.duplicateUserName?.let { Text(text = it) }
        duplicationUserInfo?.duplicateEmail?.let { Text(text = it) }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            AccountTextInput(
                hintOutput = "Введите логин",
                errorText = if (duplicationUserInfo?.duplicateUserName != null) "Данный логин занят другим пользователем" else "Проверьте правильность введенного логина",
                validationEnteredText = if (duplicationUserInfo?.duplicateUserName != null) true else loginValidation,
                enteredText = { enteredText -> login = enteredText },
            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                hintOutput = "Введите почту",
                errorText = if (duplicationUserInfo?.duplicateEmail != null) "Аккаунт с такой почтой уже существует" else "Проверьте правильность введенной почты",
                validationEnteredText = if (duplicationUserInfo?.duplicateEmail != null) true else emailValidation,
                enteredText = { enteredText -> email = enteredText },
            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                hintOutput = "Введите пароля",
                errorText = "Проверьте правильность введенного пароля",
                validationEnteredText = passwordValidation,
                enteredText = { enteredText -> password = enteredText },
            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                hintOutput = "Введите пароль повторно",
                errorText = "Проверьте правильность введенного пароля аунтификации",
                validationEnteredText = verificationPasswordValidation,
                enteredText = { enteredText -> verificationPassword = enteredText },
            )
        }
        Spacer(modifier = Modifier.height(52.dp))

        if (isLoading) {
            CircularProgressIndicator(
                color = SoftGray,
                strokeWidth = 1.5.dp
            )
        } else {
            PushButton(
                onClick = {
                    applicationUserViewModel.clearApplicationUserViewModel()
                    applicationUserViewModel.registerApplicationUser(
                        user = ApplicationUser(
                            userName = login,
                            email = email,
                            password = password,
                            roles = listOf("User")
                        )
                    )
                    coroutineScope.launch {
                        isLoading = true
                        delay(1000)
                        if (duplicationUserInfo?.duplicateUserName != null || duplicationUserInfo?.duplicateEmail != null || password != verificationPassword) {
                            isLoading = false
                        } else {
                            applicationUserViewModel.authorizeUser(username = login, password = password)
                            delay(1000)
                            if (tokenManager.getToken() != null) controller.navigate(AppNavigationRoute.BottomAppNavigationBar.route) else isLoading = false
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
                controller.navigate(AppNavigationRoute.AuthorizationScreen.route)
            },
            text = stringResource(R.string.log_in_to_an_existing_text),
            style = TextStyle(
                fontSize = 14.sp,
                color = SoftGray
            )
        )
    }
}

@Composable
private fun RegistrationButton(
    textButton: String,
    fieldCheck: Boolean,
    controller: NavController,
    route: String,
    onButtonClick: (Boolean) -> Unit,
    fieldValidityCheck: Boolean,
    textUserNameField: String,
    textEmailField: String,
    textPasswordField: String,
    textRetryPasswordField: String,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {

    val user: ApplicationUser =
        ApplicationUser(textUserNameField, textEmailField, textPasswordField, listOf("User"))

    Box(
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(12.dp))
            .border(width = 0.3.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                applicationUserViewModel.registerApplicationUser(
                    user
                )
                controller.navigate(route)
//                if (fieldValidityCheck) {
//                }
//                if (fieldCheck) onButtonClick(true) else onButtonClick(
//                    false
//                )
            },
    ) {
        Text(
            text = textButton,
            style = TextStyle(
                fontSize = 20.sp,
                color = if (fieldCheck) SoftGray else LightGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 64.dp, vertical = 12.dp)
        )
    }
}