package com.den.culinarychest.presentation.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
fun AuthorizationScreen(
    controller: NavController
) {

    Authorization(controller = controller)
}


@Composable
fun Authorization(
    controller: NavController
) {

    // Придумать как оптимизировать эти переменные

    var textEmailField by remember { mutableStateOf("") }
    var textPasswordField by remember { mutableStateOf("") }

    val isEmailValid by remember { derivedStateOf { textEmailField.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(textEmailField).matches() } }
    val isPasswordValid by remember { derivedStateOf { textPasswordField.isNotEmpty() && textPasswordField.length >= 8 } }

    val isEmailNotEmptyAndValid by remember { derivedStateOf { textEmailField.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(textEmailField).matches() } }
    val isPasswordNotEmptyAndValid by remember { derivedStateOf { textPasswordField.isNotEmpty() && textPasswordField.length >=  8 } }

    val hasValidInput by remember { derivedStateOf { isEmailValid || isPasswordValid } }
    val allFieldsAreValid by remember { derivedStateOf { isEmailNotEmptyAndValid && isPasswordNotEmptyAndValid } }

    var checkTextOnClick by remember { mutableStateOf(false) }

    var emailVerificationResult by remember { mutableStateOf(false) }
    var passwordVerificationResult by remember { mutableStateOf(false) }

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
            TextInput(
                outputTextHint = stringResource(R.string.email_text),
                onTextChanged = { inputEmail -> textEmailField = inputEmail },
                onTextValidation = { text -> Patterns.EMAIL_ADDRESS.matcher(text).matches() },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> emailVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextInput(
                outputTextHint = stringResource(R.string.verification_text),
                onTextChanged = { inputPass -> textPasswordField = inputPass },
                onTextValidation = { text -> text.length >= 8 },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> passwordVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        PushButton(
            textButton = stringResource(R.string.enter_text),
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
