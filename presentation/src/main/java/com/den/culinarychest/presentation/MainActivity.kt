package com.den.culinarychest.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Lifecycling
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.den.culinarychest.presentation.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeStepsViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeStepsRepositoryImpl

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
                        ApplicationUserFavoriteRecipeRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi),
                        applicationUserViewModel = applicationUserViewModel
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
        Log.d("AAA", "onCreate")
        setContent {
            CulinaryChestTheme {
                AppNavigation(
                    applicationUserViewModel,
                    recipeViewModel,
                    applicationUserFavoriteRecipeViewModel,
                    applicationUserRecipeViewModel,
                    recipeStepsViewModel,
                    TokenManager(this)
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        applicationUserViewModel.clear()
        recipeViewModel.clear()
        applicationUserFavoriteRecipeViewModel.clear()
        applicationUserRecipeViewModel.clear()
        recipeStepsViewModel.clear()
        Log.d("AAA", "onStop")
    }

}

