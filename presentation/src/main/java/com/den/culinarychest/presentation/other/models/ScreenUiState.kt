package com.den.culinarychest.presentation.other.models

import androidx.compose.ui.text.input.TextFieldValue

data class ScreenUiState(
    var textUserNameField: String = "",
    var textEmailField: String = "",
    var textPasswordField: String = "",
    var textRetryPasswordField: String = ""
)
