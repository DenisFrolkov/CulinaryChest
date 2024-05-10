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
import com.den.culinarychest.presentation.common.TextInput.AccountTextInput
import com.den.culinarychest.presentation.models.ScreenUiState
import com.den.culinarychest.presentation.route.AppNavigationRoute
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftPink
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.example.culinarychest.data.data.repository.TokenManager

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
    var uiState by remember { mutableStateOf(ScreenUiState()) }

    val isUserNameValid by remember {
        derivedStateOf {
            uiState.textUserNameField.isEmpty()
        }
    }
    val isPasswordValid by remember { derivedStateOf { uiState.textPasswordField.isNotEmpty() && uiState.textPasswordField.length >= 13 } }

    val isUserNameNotEmptyAndValid by remember {
        derivedStateOf {
            uiState.textUserNameField.isNotEmpty()
        }
    }
    val isPasswordNotEmptyAndValid by remember { derivedStateOf { uiState.textPasswordField.isNotEmpty() && uiState.textPasswordField.length >= 13 } }

    val hasValidInput by remember { derivedStateOf { isUserNameValid || isPasswordValid } }
    val allFieldsAreValid by remember { derivedStateOf { isUserNameNotEmptyAndValid && isPasswordNotEmptyAndValid } }

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
            AccountTextInput(
                outputTextHint = stringResource(R.string.user_name_text),
                onTextChanged = { uiState = uiState.copy(textUserNameField = it) },
                onTextValidation = { text -> Patterns.EMAIL_ADDRESS.matcher(text).matches() },
                checkTextOnClick = checkTextOnClick,
                transferVerification = { newShow -> checkTextOnClick = newShow },
                returnValidation = { validation -> emailVerificationResult = validation }
            )
            Spacer(modifier = Modifier.height(16.dp))
            AccountTextInput(
                outputTextHint = stringResource(R.string.password_text),
                onTextChanged = { uiState = uiState.copy(textPasswordField = it) },
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
            fieldValidityCheck = allFieldsAreValid,
            uiState.textUserNameField,
            uiState.textPasswordField,
            applicationUserViewModel,
            tokenManager
        )
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