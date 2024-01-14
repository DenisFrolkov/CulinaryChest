package com.den.culinarychest.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var textParameter by remember { mutableStateOf("") }
    var activeParameter by remember { mutableStateOf(false) }
    val recipeElements = remember {
        mutableStateListOf(
            "Пирог с вишней",
            "Рис с мясом"
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
//            .offset(y = -10.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 0.15.dp, color = SoftGray, shape = RoundedCornerShape(12.dp))
                .background(color = SoftOrange, shape = RoundedCornerShape(12.dp)),
            colors = SearchBarDefaults.colors(
                containerColor = SoftOrange
            ),
            query = textParameter,
            onQueryChange = {
                textParameter = it
            },
            onSearch = {
                recipeElements.add(it)
                activeParameter = false
                textParameter = ""
            },
            active = activeParameter,
            onActiveChange = {
                activeParameter = it
            },
            placeholder = {
                Text(
                    text = "Поиск",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = SoftGray
                    )
                )
            },
            trailingIcon = {
                val icon = if (activeParameter) Icons.Default.Clear else Icons.Default.Search
                val contentDescription =
                    if (activeParameter) "Тут должна быть иконка крестика" else "Тут должна быть иконка поиска"
                val tint = if (activeParameter) SoftGray else SoftGray

                Icon(
                    modifier = Modifier
                        .clickable {
                            if (textParameter.isNotEmpty()) {
                                textParameter = ""
                            } else activeParameter = false
                        },
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = tint
                )
            },
        ) {
            recipeElements.forEach {
                Row(
                    modifier = Modifier
                        .padding(all = 14.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = "Тут должна быть иконка истории"
                    )
                    Text(text = it)
                }
            }
        }
    }
}
