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
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.CreateApplicationUserRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.DeleteApplicationUserRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.GetApplicationUserRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.UpdateApplicationUserRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.AuthorizationApplicationUserUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.GetApplicationUserInfoUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.RegistrationApplicationUserUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.CreateRecipeStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.DeleteRecipeStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.GetRecipeStepsUseCases
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.UpdateRecipeStepUseCase

class MainActivity : ComponentActivity() {

    val tokenManager = TokenManager(this)

    private val applicationUserViewModel by viewModels<ApplicationUserViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ApplicationUserViewModel(
                    TokenManager(this@MainActivity),
                    RegistrationApplicationUserUseCase(
                        ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    ),
                    AuthorizationApplicationUserUseCase(
                        ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    ),
                    GetApplicationUserInfoUseCase(
                        ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                    ),
                ) as T
            }
        }
    })

    private val recipeViewModel by viewModels<RecipeViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeViewModel(
                    GetRecipeByIdUseCase(
                        RecipeRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    GetRecipesByIdsUseCase(
                        RecipeRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    GetRecipesUseCase(
                        RecipeRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
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
                        GetApplicationUserFavoriteRecipesUseCase(
                            ApplicationUserFavoriteRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        GetFavoriteRecipeByRecipeIdUseCase(
                            ApplicationUserFavoriteRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        CreateApplicationUserFavoriteRecipesUseCase(
                            ApplicationUserFavoriteRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        DeleteApplicationUserFavoriteRecipeUseCase(
                            ApplicationUserFavoriteRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        )
                    )
                            as T
                }
            }
        })

    private val applicationUserRecipeViewModel by viewModels<ApplicationUserRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ApplicationUserRecipeViewModel(
                        GetApplicationUserRecipesUseCase(
                            ApplicationUserRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        CreateApplicationUserRecipeUseCase(
                            ApplicationUserRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        DeleteApplicationUserRecipeUseCase(
                            ApplicationUserRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        ),
                        UpdateApplicationUserRecipeUseCase(
                            ApplicationUserRecipeRepositoryImpl(
                                RetrofitInstance(tokenManager)
                                    .culinaryChestApi
                            )
                        )
                    )
                            as T
                }
            }
        })

    private val recipeStepsViewModel by viewModels<RecipeStepsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecipeStepsViewModel(
                    CreateRecipeStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    DeleteRecipeStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    GetRecipeStepsUseCases(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    UpdateRecipeStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    )
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

