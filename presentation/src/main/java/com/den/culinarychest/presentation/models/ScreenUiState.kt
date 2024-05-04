package com.den.culinarychest.presentation.models

import androidx.compose.ui.text.input.TextFieldValue

data class ScreenUiState(
    var textUserNameField: String = "",
    var textEmailField: String = "",
    var textPasswordField: String = "",
    var textRetryPasswordField: String = ""
)
