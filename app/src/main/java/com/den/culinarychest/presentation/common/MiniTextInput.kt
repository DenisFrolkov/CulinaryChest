package com.den.culinarychest.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun MiniTextInput(
    inputText: String,
    onTextChanged: (String) -> Unit,
    validation: (String) -> Boolean
) {
    val stringResource = LocalContext.current.resources

    var text by remember { mutableStateOf(TextFieldValue()) }
    var isHintVisible by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        isError = !validation(text.text)
        onTextChanged(text.text)
    }

    if (text.text.isEmpty()) isError = false


    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(14.dp))
            .background(color = SoftOrange, shape = RoundedCornerShape(14.dp))
            .padding(start = 12.dp, top = 15.dp, end = 12.dp, bottom = 16.dp)
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                isHintVisible = it.text.isEmpty()
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp)
                .align(Alignment.CenterStart)
        )
        if (isHintVisible) {
            Text(
                text = inputText,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = SoftGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
        }
        if (isError) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mistake_icon),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}