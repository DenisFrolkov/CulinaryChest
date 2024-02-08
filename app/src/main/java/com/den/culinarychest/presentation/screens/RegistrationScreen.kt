package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.PushButton
import com.den.culinarychest.presentation.common.TextInput
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
    val stringResource = LocalContext.current.resources

    var login by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var verification by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(112.dp))
        Text(
            text = stringResource.getString(R.string.registration_text),
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
                inputText = stringResource.getString(R.string.login_text),
                onTextChanged = { login = it },
                validation = { it.matches(Regex("[a-zA-Z0-9_]+")) && it.length in 5..20 }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource.getString(R.string.email_text),
                onTextChanged = { email = it },
                validation = { it.matches(Regex("^([a-zA-Z0-9_\\-]+)@([a-zA-Z0-9_\\-]+)\\.([a-zA-Z]{2,5})\$")) }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource.getString(R.string.password_text),
                onTextChanged = { password = it },
                validation = { it.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$")) }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                inputText = stringResource.getString(R.string.verification_text),
                onTextChanged = { verification = it },
                validation = { it == password }
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        PushButton(
            textButton = stringResource.getString(R.string.new_account_text),
            transitionalText = stringResource.getString(R.string.log_in_to_an_existing_text),
            navController = navController,
            navigationButton = AppNavigationRoute.BottomAppNavigationBar.route,
            navigationText = AppNavigationRoute.AuthorizationScreen.route
        )
    }
}
