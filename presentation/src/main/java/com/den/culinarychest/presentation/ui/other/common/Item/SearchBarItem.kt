package com.den.culinarychest.presentation.other.common.Item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.main.viewmodel.SearchViewModel
import com.example.culinarychest.data.data.repository.TokenManager

@Composable
fun SearchBarItem(
    searchViewModel: SearchViewModel,
    tokenManager: TokenManager,
    onTextChanged: (String) -> Unit,
) {

    var enteredSearchText by remember { mutableStateOf(TextFieldValue()) }
    var isHintVisible by remember { mutableStateOf(true) }
    var isHistoryVisible by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(enteredSearchText) {
        onTextChanged(enteredSearchText.text)
    }
    Column {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SoftOrange,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 0.1.dp,
                    color = SoftGray,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 16.dp)
        ) {
            BasicTextField(
                value = enteredSearchText,
                onValueChange = {
                    enteredSearchText = it
                    isHintVisible = it.text.isBlank()
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                singleLine = true,
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        tokenManager.getToken()?.let { token -> searchViewModel.getListRecipes(token, enteredSearchText.text) }
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused ->
                        if (isFocused.isFocused) {
                            isHistoryVisible = true
                        } else {
                            isHistoryVisible = false
                        }
                    }
                    .align(Alignment.CenterStart)
                    .padding(end = 40.dp)
            )
            if (enteredSearchText.text.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.search_text),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = LightGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}
