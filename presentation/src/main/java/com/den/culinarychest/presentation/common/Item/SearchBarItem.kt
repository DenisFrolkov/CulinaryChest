package com.den.culinarychest.presentation.common.Item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.LightGray
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@Composable
fun SearchBarItem(
    onTextChanged: (String) -> Unit,
) {
    var enteredSearchText by remember { mutableStateOf(TextFieldValue()) }
    var isHintVisible by remember { mutableStateOf(true) }
    var isHistoryVisible by remember { mutableStateOf(false) }

    val searchHistoryCollection = mutableListOf(
        "Блюдо 1",
        "Блюдо 2",
        "Блюдо 3"
    )

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
                    shape =
                    if (isHistoryVisible) {
                        RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    } else RoundedCornerShape(14.dp)
                )
                .border(
                    width = 0.1.dp,
                    color = SoftGray,
                    shape =
                    if (isHistoryVisible) {
                        RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    } else RoundedCornerShape(14.dp)
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
            if (enteredSearchText.text.isEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.search_searchbar_icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                )
            }
            if (enteredSearchText.text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.clear_searchbar_icon),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(18.dp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            enteredSearchText = TextFieldValue()
                            isHintVisible = true
                        }
                )
            }
        }
        if (isHistoryVisible) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SoftOrange)
                    .border(
                        width = .5.dp,
                        color = SoftGray,
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                    )
                    .padding(10.dp)
            ) {
                Divider(color = Color.Gray, thickness = .5.dp)
                searchHistoryCollection.forEach { historyTextItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                enteredSearchText = TextFieldValue(historyTextItem)
                            }
                    ) {
                        Text(
                            text = historyTextItem,
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = SoftGray
                            ),
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    Divider(color = Color.Gray, thickness = .5.dp)
                }
            }
        }
    }
}
