package com.den.culinarychest.presentation.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.den.culinarychest.presentation.common.TextInput.TextInput
import com.den.culinarychest.presentation.route.AppNavigationRoute
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
    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verification by remember { mutableStateOf("") }

    val isLoginValid by remember { derivedStateOf { login.matches(Regex("[a-zA-Z0-9_]+")) && login.length in 5..20 } }
    val isEmailValid by remember {
        derivedStateOf {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
    val isPasswordValid by remember { derivedStateOf { password.length >= 8 } }
    val isVerificationPasswordValid by remember { derivedStateOf { verification == password } }

    val inputTexts by remember { derivedStateOf { isLoginValid && isEmailValid && isPasswordValid && isVerificationPasswordValid } }

    var pushButtonValue by remember { mutableStateOf(false) }


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
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TextInput(
                inputText = stringResource(R.string.login_text),
                onTextChanged = { login = it },
                validation = { it.matches(Regex("[a-zA-Z0-9_]+")) && it.length in 5..20 },
                initialValue = pushButtonValue
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource(R.string.email_text),
                onTextChanged = { email = it },
                validation = { text -> Patterns.EMAIL_ADDRESS.matcher(text).matches() },
                initialValue = pushButtonValue
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource(R.string.password_text),
                onTextChanged = { password = it },
                validation = { text -> text.length >= 8 },
                initialValue = pushButtonValue
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource(R.string.verification_text),
                onTextChanged = { verification = it },
                validation = { text -> text == password },
                initialValue = pushButtonValue
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        PushButton(
            textButton = stringResource(R.string.new_account_text),
            inputTexts = inputTexts,
            navController = navController,
            navigationButton = AppNavigationRoute.BottomAppNavigationBar.route,
            onButtonClick = { newValue -> pushButtonValue = newValue }
        )
        Spacer(modifier = Modifier.height(height = 6.dp))
        Text(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                navController.navigate(AppNavigationRoute.AuthorizationScreen.route)
            },
            text = stringResource(R.string.log_in_to_an_existing_text),
            style = TextStyle(
                fontSize = 14.sp,
                color = SoftGray
            )
        )
    }
}
