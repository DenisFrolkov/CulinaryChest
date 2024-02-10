package com.den.culinarychest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.den.culinarychest.presentation.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {
                AppNavigation()
            }
        }
    }
}