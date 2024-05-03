package com.den.culinarychest.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.view_models.ApplicationUserFavoriteRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserRecipeViewModel
import com.den.culinarychest.presentation.view_models.ApplicationUserViewModel
import com.den.culinarychest.presentation.view_models.RecipeStepsViewModel
import com.den.culinarychest.presentation.view_models.RecipeViewModel
import com.example.culinarychest.data.data.TokenManager
import com.example.culinarychest.data.data.api.RetrofitInstance
import com.example.culinarychest.data.data.repository.ApplicationUserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.data.repository.RecipeStepsRepositoryImpl
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.dataclasses.step.CreateStep
import com.example.culinarychest.domain.domain.dataclasses.step.Step
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

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
                    TokenManager(this)
                )
            }
        }
    }

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

    private val applicationUserFavoriteRecipeViewModel by viewModels<ApplicationUserFavoriteRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserFavoriteRecipeViewModel(
                        ApplicationUserFavoriteRecipeRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi),
                        applicationUserViewModel = applicationUserViewModel
                    )
                            as T
                }
            }
        }
    )

    private val applicationUserRecipeViewModel by viewModels<ApplicationUserRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserRecipeViewModel(
                        ApplicationUserRecipeRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    )
                            as T
                }
            }
        }
    )

    private val recipeStepsViewModel by viewModels<RecipeStepsViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeStepsViewModel(
                        RecipeStepsRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    )
                            as T
                }
            }
        }
    )
}