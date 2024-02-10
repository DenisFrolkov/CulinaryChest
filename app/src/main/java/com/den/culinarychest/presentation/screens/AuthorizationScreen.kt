package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
fun AuthorizationScreen(
    navController: NavController
) {
    Authorization(navController = navController)
}


@Composable
fun Authorization(navController: NavController) {

    val stringResource = LocalContext.current.resources

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(color = SoftPink)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource.getString(R.string.authorization_text),
            style = TextStyle(
                fontSize = 24.sp,
                color = SoftGray
            ),
            modifier = Modifier.padding(bottom = 60.dp)
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
        TextInput(
            inputText = stringResource.getString(R.string.email_text),
            onTextChanged = { inputEmail -> email = inputEmail }
        ) { text ->
            android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
        }
            Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            inputText = stringResource.getString(R.string.verification_text),
            onTextChanged = { inputPass -> password = inputPass }
        ) { text ->
            text.length >= 8
        }
            Spacer(modifier = Modifier.height(32.dp))
        }
        PushButton(
            textButton = stringResource.getString(R.string.enter_text),
            transitionalText = stringResource.getString(R.string.new_account_text),
            navController = navController,
            navigationButton = AppNavigationRoute.BottomAppNavigationBar.route,
            navigationText = AppNavigationRoute.RegistrationScreen.route
        )
    }
}
