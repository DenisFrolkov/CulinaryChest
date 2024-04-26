package com.den.culinarychest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl

class MainActivity : ComponentActivity() {

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(RecipeRepositoryImpl(RetrofitInstance.culinaryChestApi))
                        as T
            }
        }
    })

    private val applicationUserViewModel by viewModels<ApplicationUserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ApplicationUserViewModel(ApplicationUserRepositoryImpl(
                    RetrofitInstance.culinaryChestApi
                ))
                        as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {

//                val recipes by recipeViewModel.recipes.collectAsState()
                val username = "Denis123456"
                val email = "denis12345@mail.ru"
                val password = "1234567891234"
                val roles = listOf("User")

                Column {
                    LaunchedEffect(Unit) {
                        applicationUserViewModel.authorizeUser(username, password)
                    }
                }

//                Button(onClick = { applicationUserViewModel.registerApplicationUser(username, email, password, roles) }) {
//
//                }
//                AppNavigation()
            }
        }
    }
}