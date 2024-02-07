package com.den.culinarychest.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.den.culinarychest.presentation.ui.theme.SoftOrange
import com.den.culinarychest.R
import com.den.culinarychest.presentation.ui.theme.SoftGray

@Composable
fun FAB() {
    Box(
        modifier = Modifier
            .background(color = SoftOrange, shape = RoundedCornerShape(size = 25.dp))
            .border(width = 0.1.dp, color = SoftGray, shape = RoundedCornerShape(size = 25.dp))
            .size(size = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(size = 20.dp),
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = null
        )
    }
}