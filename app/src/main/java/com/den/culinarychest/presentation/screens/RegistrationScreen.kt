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
    controller: NavController
) {
    Registration(
        controller = controller
    )
}

@Composable
fun Registration(
    controller: NavController
) {

    // Придумать как оптимизировать эти переменные

    var textLoginField by remember { mutableStateOf("") }
    var textEmailField by remember { mutableStateOf("") }
    var textPasswordField by remember { mutableStateOf("") }
    var textRetryPasswordField by remember { mutableStateOf("") }

    val isLoginValid by remember { derivedStateOf { textLoginField.isEmpty() || textLoginField.matches(Regex("[a-zA-Z0-9_]+")) && textLoginField.length in 5..20 } }
    val isEmailValid by remember { derivedStateOf { textEmailField.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(textEmailField).matches() } }
    val isPasswordValid by remember { derivedStateOf { textPasswordField.isEmpty() || textPasswordField.length >= 8 } }
    val isRetryPasswordValid by remember { derivedStateOf { textRetryPasswordField.isEmpty() && textRetryPasswordField == textPasswordField } }

    val isLoginNotEmptyAndValid by remember { derivedStateOf { textLoginField.isNotEmpty() && textLoginField.matches(Regex("[a-zA-Z0-9_]+")) && textLoginField.length in 5..20 } }
    val isEmailNotEmptyAndValid by remember { derivedStateOf { textEmailField.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(textEmailField).matches() } }
    val isPasswordNotEmptyAndValid by remember { derivedStateOf { textPasswordField.isNotEmpty() && textPasswordField.length >= 8 } }
    val isRetryPasswordNotEmptyAndValid by remember { derivedStateOf { textRetryPasswordField.isNotEmpty() && textRetryPasswordField == textPasswordField } }

    val hasValidInput by remember { derivedStateOf { isLoginValid || isEmailValid || isPasswordValid || isRetryPasswordValid } }
    val allFieldsAreValid by remember { derivedStateOf { isLoginNotEmptyAndValid && isEmailNotEmptyAndValid && isPasswordNotEmptyAndValid && isRetryPasswordNotEmptyAndValid } }

    var checkTextOnClick by remember { mutableStateOf(false) }

    var loginVerificationResult by remember { mutableStateOf(false) }
    var emailVerificationResult by remember { mutableStateOf(false) }
    var passwordVerificationResult by remember { mutableStateOf(false) }
    var passwordRetryVerificationResult by remember { mutableStateOf(false) }

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
                outputTextHint = stringResource(R.string.login_text),
                onTextChanged = { textLoginField = it },
                onTextValidation = { it.matches(Regex("[a-zA-Z0-9_]+")) && it.length in 5..20 },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> loginVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                outputTextHint = stringResource(R.string.email_text),
                onTextChanged = { textEmailField = it },
                onTextValidation = { text -> Patterns.EMAIL_ADDRESS.matcher(text).matches() },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> emailVerificationResult = validation }

            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                outputTextHint = stringResource(R.string.password_text),
                onTextChanged = { textPasswordField = it },
                onTextValidation = { text -> text.length >= 8 },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> passwordVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextInput(
                outputTextHint = stringResource(R.string.verification_text),
                onTextChanged = { textRetryPasswordField = it },
                onTextValidation = { text -> text.isNotEmpty() && text == textPasswordField },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> passwordRetryVerificationResult = validation }
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        PushButton(
            textButton = stringResource(R.string.new_account_text),
            fieldCheck = hasValidInput,
            controller = controller,
            route = AppNavigationRoute.BottomAppNavigationBar.route,
            onButtonClick = { newValue -> checkTextOnClick = newValue },
            fieldValidityCheck = allFieldsAreValid
        )
        Spacer(modifier = Modifier.height(height = 6.dp))
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
