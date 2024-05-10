package com.den.culinarychest.presentation.screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.den.culinarychest.presentation.common.TextInput.AccountTextInput
import com.den.culinarychest.presentation.models.ScreenUiState
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser

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

@Composable
fun Registration(
    controller: NavController,
    applicationUserViewModel: ApplicationUserViewModel,
    tokenManager: TokenManager
) {

    var uiState by remember { mutableStateOf(ScreenUiState()) }

    val isLoginValid by remember {
        derivedStateOf {
            uiState.textUserNameField.isEmpty() || uiState.textUserNameField.matches(
                Regex("[a-zA-Z0-9_]+")
            ) && uiState.textUserNameField.length in 5..20
        }
    }
    val isEmailValid by remember {
        derivedStateOf {
            uiState.textEmailField.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(
                uiState.textEmailField
            ).matches()
        }
    }
    val isPasswordValid by remember { derivedStateOf { uiState.textPasswordField.isEmpty() || uiState.textPasswordField.length >= 8 } }
    val isRetryPasswordValid by remember { derivedStateOf { uiState.textRetryPasswordField.isEmpty() && uiState.textRetryPasswordField == uiState.textPasswordField } }

    val isLoginNotEmptyAndValid by remember {
        derivedStateOf {
            uiState.textUserNameField.isNotEmpty() && uiState.textUserNameField.matches(
                Regex("[a-zA-Z0-9_]+")
            ) && uiState.textUserNameField.length in 5..20
        }
    }
    val isEmailNotEmptyAndValid by remember {
        derivedStateOf {
            uiState.textEmailField.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                uiState.textEmailField
            ).matches()
        }
    }
    val isPasswordNotEmptyAndValid by remember { derivedStateOf { uiState.textPasswordField.isNotEmpty() && uiState.textPasswordField.length >= 8 } }
    val isRetryPasswordNotEmptyAndValid by remember { derivedStateOf { uiState.textRetryPasswordField.isNotEmpty() && uiState.textRetryPasswordField == uiState.textPasswordField } }

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
            AccountTextInput(
                outputTextHint = stringResource(R.string.user_name_text),
                onTextChanged = { uiState = uiState.copy(textUserNameField = it) },
                onTextValidation = { it.matches(Regex("[a-zA-Z0-9_]+")) && it.length in 5..20 },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> loginVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                outputTextHint = stringResource(R.string.email_text),
                onTextChanged = { uiState = uiState.copy(textEmailField = it) },
                onTextValidation = { text -> Patterns.EMAIL_ADDRESS.matcher(text).matches() },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> emailVerificationResult = validation }

            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                outputTextHint = stringResource(R.string.password_text),
                onTextChanged = { uiState = uiState.copy(textPasswordField = it) },
                onTextValidation = { text -> text.length >= 8 },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> passwordVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(32.dp))
            AccountTextInput(
                outputTextHint = stringResource(R.string.verification_text),
                onTextChanged = { uiState = uiState.copy(textPasswordField = it) },
                onTextValidation = { text -> text.isNotEmpty() && text == uiState.textPasswordField },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> passwordRetryVerificationResult = validation }
            )
        }
        Spacer(modifier = Modifier.height(52.dp))
        RegistrationButton(
            textButton = stringResource(R.string.new_account_text),
            fieldCheck = hasValidInput,
            controller = controller,
            route = AppNavigationRoute.AuthorizationScreen.route,
            onButtonClick = { newValue -> checkTextOnClick = newValue },
            fieldValidityCheck = allFieldsAreValid,
            uiState.textUserNameField,
            uiState.textEmailField,
            uiState.textPasswordField,
            uiState.textRetryPasswordField,
            applicationUserViewModel,
            tokenManager
        )
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

    val user: ApplicationUser = ApplicationUser(textUserNameField, textEmailField, textPasswordField, listOf("User"))

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