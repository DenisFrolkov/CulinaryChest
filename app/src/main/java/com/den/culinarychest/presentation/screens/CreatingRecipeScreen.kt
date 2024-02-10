package com.den.culinarychest.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.den.culinarychest.R
import com.den.culinarychest.presentation.common.MiniTextInput
import com.den.culinarychest.presentation.common.TextInput
import com.den.culinarychest.presentation.ui.theme.SoftGray
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.presentation.ui.theme.SoftPink

@Composable
fun CreatingRecipeScreen(
    navController: NavController
) {
    CreatingRecipe(navController = navController)
}

@Composable
fun CreatingRecipe(
    navController: NavController
) {

    var text by remember { mutableStateOf("") }

    val stringResource = LocalContext.current.resources

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = SoftPink)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = SoftOrange)
                    .border(width = 0.1.dp, color = SoftGray)
                    .padding(horizontal = 12.dp, vertical = 14.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.popBackStack()
                        }
                )
            }
            Image(
                painter = painterResource(id = R.drawable.add_recipe_photo),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
                    ) { }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                TextInput(
                    inputText = stringResource.getString(R.string.enter_title_recipe),
                    onTextChanged = { text = it }
                ) { it.matches(Regex("[а-яА-Я0-9_]+")) && it.length in 5..20 }
                Spacer(modifier = Modifier.height(height = 10.dp))
                TextInput(
                    inputText = stringResource.getString(R.string.enter_ingredients_recipe),
                    onTextChanged = { text = it }
                ) { it.matches(Regex("[а-яА-Я]+")) && it.length in 5..20 }
                Spacer(modifier = Modifier.height(height = 10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    Text(
                        text = stringResource.getString(R.string.enter_time_recipe),
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = SoftGray
                        ),
                        modifier = Modifier.padding(vertical = 10.dp).padding(end = 4.dp)
                    )
                    Box(modifier = Modifier) {
                        MiniTextInput(
                            inputText = stringResource.getString(R.string.nothing),
                            onTextChanged = { text = it }
                        ) { it.matches(Regex("[0-9]+"))}
                    }
                }
            }
        }
    }
}