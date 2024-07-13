package com.den.culinarychest.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.other.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.other.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.main.viewModels.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.main.viewModels.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.main.viewModels.ApplicationUserViewModel
import com.den.culinarychest.presentation.main.viewModels.RecipeStepsViewModel
import com.den.culinarychest.presentation.main.viewModels.RecipeViewModel
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeStepsRepositoryImpl
import com.example.culinarychest.data.data.repository.TokenManager

class MainActivity : ComponentActivity() {

    val tokenManager = TokenManager(this)

    private val applicationUserViewModel by viewModels<ApplicationUserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ApplicationUserViewModel(
                    TokenManager(this@MainActivity),
                    ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                ) as T
            }
        }
    })

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(
                    RecipeRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                )
                        as T
            }
        }
    })

    private val applicationUserFavoriteRecipeViewModel by viewModels<ApplicationUserFavoriteRecipeViewModel>( factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserFavoriteRecipeViewModel(
                        ApplicationUserFavoriteRecipeRepositoryImpl(RetrofitInstance(tokenManager)
                            .culinaryChestApi)
                    )
                            as T
                }
            }
        })

    private val applicationUserRecipeViewModel by viewModels<ApplicationUserRecipeViewModel>( factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserRecipeViewModel(
                        ApplicationUserRecipeRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    )
                            as T
                }
            }
        })

    private val recipeStepsViewModel by viewModels<RecipeStepsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeStepsViewModel(
                    RecipeStepsRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                )
                        as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulinaryChestTheme {
                AppNavigation(
                    applicationUserViewModel,
                    recipeViewModel,
                    applicationUserFavoriteRecipeViewModel,
                    applicationUserRecipeViewModel,
                    recipeStepsViewModel,
                    tokenManager
                )
            }
        }
    }
}

