package com.den.culinarychest.presentation.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.ui.other.common.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.AuthorizationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageOtherRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatingRecipeViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.CreatedViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.FavoriteViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.common.GenericViewModelFactory
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageRecipeUserViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.profile.ProfileViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.RecipeDetailsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.ManageStepsViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.auth.RegistrationViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.SearchViewModel
import com.example.culinarychest.data.api.RetrofitInstance
import com.example.culinarychest.data.repository.ApplicationUserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.repository.ApplicationUserRecipeRepositoryImpl
import com.example.culinarychest.data.repository.ApplicationUserRepositoryImpl
import com.example.culinarychest.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.repository.RecipeStepsRepositoryImpl
import com.example.culinarychest.data.repository.TokenManager
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.GetApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases.CreateApplicationUserRecipeUseCase
import com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases.DeleteApplicationUserRecipeUseCase
import com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases.GetApplicationUserRecipesUseCase
import com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases.UpdateApplicationUserRecipeUseCase
import com.example.culinarychest.domain.usecase.applicationUserUseCases.AuthorizationApplicationUserUseCase
import com.example.culinarychest.domain.usecase.applicationUserUseCases.GetApplicationUserInfoUseCase
import com.example.culinarychest.domain.usecase.applicationUserUseCases.RegistrationApplicationUserUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.UpdateStepUseCase

class MainActivity : ComponentActivity() {

    val tokenManager = TokenManager(this)

    private val registrationApplicationUserViewModel by viewModels<RegistrationViewModel> {
        GenericViewModelFactory {
            RegistrationViewModel(
                RegistrationApplicationUserUseCase(
                    ApplicationUserRepositoryImpl(
                        RetrofitInstance(
                            tokenManager
                        ).culinaryChestApi
                    )
                )
            )
        }
    }


    private val authorizationApplicationUserViewModel by viewModels<AuthorizationViewModel> {
        GenericViewModelFactory {
            AuthorizationViewModel(
                tokenManager,
                AuthorizationApplicationUserUseCase(
                    ApplicationUserRepositoryImpl(
                        RetrofitInstance(
                            tokenManager
                        ).culinaryChestApi
                    )
                )
            )
        }
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        GenericViewModelFactory {
            SearchViewModel(
                GetRecipesUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val recipeDetailsViewModel by viewModels<RecipeDetailsViewModel> {
        GenericViewModelFactory {
            RecipeDetailsViewModel(
                GetFavoriteRecipeByRecipeIdUseCase(
                    ApplicationUserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                ),
                GetRecipeByIdUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val createdViewModel by viewModels<CreatedViewModel> {
        GenericViewModelFactory {
            CreatedViewModel(
                GetApplicationUserRecipesUseCase(
                    ApplicationUserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                )
            )
        }
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        GenericViewModelFactory {
            FavoriteViewModel(
                GetApplicationUserFavoriteRecipesUseCase(
                    ApplicationUserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                ),
                GetRecipesByIdsUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val profileViewModel by viewModels<ProfileViewModel> {
        GenericViewModelFactory {
            ProfileViewModel(
                GetApplicationUserInfoUseCase(
                    ApplicationUserRepositoryImpl(
                        RetrofitInstance(
                            tokenManager
                        ).culinaryChestApi
                    )
                ),
                GetApplicationUserRecipesUseCase(
                    ApplicationUserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                ),
                GetApplicationUserFavoriteRecipesUseCase(
                    ApplicationUserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                )
            )
        }
    }

    private val creatingRecipeViewModel by viewModels<CreatingRecipeViewModel> {
        GenericViewModelFactory {
            CreatingRecipeViewModel(
                CreateApplicationUserRecipeUseCase(
                    ApplicationUserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val manageRecipeUserViewModel by viewModels<ManageRecipeUserViewModel> {
        GenericViewModelFactory {
            ManageRecipeUserViewModel(
                UpdateApplicationUserRecipeUseCase(
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
                )
            )
        }
    }

    private val manageOtherRecipeViewModel by viewModels<ManageOtherRecipeViewModel> {
        GenericViewModelFactory {
            ManageOtherRecipeViewModel(
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
        }
    }

    private val manageStepsViewModel by viewModels<ManageStepsViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ManageStepsViewModel(
                    CreateStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    DeleteStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManager).culinaryChestApi
                        )
                    ),
                    UpdateStepUseCase(
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
                    registrationApplicationUserViewModel,
                    authorizationApplicationUserViewModel,
                    searchViewModel,
                    recipeDetailsViewModel,
                    createdViewModel,
                    favoriteViewModel,
                    profileViewModel,
                    creatingRecipeViewModel,
                    manageRecipeUserViewModel,
                    manageOtherRecipeViewModel,
                    manageStepsViewModel,
                    tokenManager
                )
            }
        }
    }
}

