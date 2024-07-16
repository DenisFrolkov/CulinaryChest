package com.den.culinarychest.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.den.culinarychest.presentation.other.navigation.appNavigation.AppNavigation
import com.den.culinarychest.presentation.other.ui.theme.CulinaryChestTheme
import com.den.culinarychest.presentation.main.viewmodel.AuthorizationViewModel
import com.den.culinarychest.presentation.main.viewmodel.ManageFavoriteRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.CreateRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.FavoriteRecipeByRecipeIdViewModel
import com.den.culinarychest.presentation.main.viewmodel.GenericViewModelFactory
import com.den.culinarychest.presentation.main.viewmodel.HorizontalPagerViewModel
import com.den.culinarychest.presentation.main.viewmodel.ManageRecipeViewModel
import com.den.culinarychest.presentation.main.viewmodel.ProfileViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeByIdViewModel
import com.den.culinarychest.presentation.main.viewmodel.RecipeOwnershipViewModel
import com.den.culinarychest.presentation.main.viewmodel.ManageStepsViewModel
import com.den.culinarychest.presentation.main.viewmodel.RegistrationViewModel
import com.den.culinarychest.presentation.main.viewmodel.SearchViewModel
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
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.GetRecipeStepsUseCases
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.UpdateStepUseCase

class MainActivity : ComponentActivity() {

    val tokenManager = TokenManager(this)

    private val registrationApplicationUserViewModel by viewModels<RegistrationViewModel> {
        GenericViewModelFactory {
            RegistrationViewModel(
                RegistrationApplicationUserUseCase(
                    ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
                )
            )
        }
    }


    private val authorizationApplicationUserViewModel by viewModels<AuthorizationViewModel> {
        GenericViewModelFactory {
            AuthorizationViewModel(
                tokenManager,
                AuthorizationApplicationUserUseCase(
                    ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
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

    private val recipeOwnershipViewModel by viewModels<RecipeOwnershipViewModel> {
        GenericViewModelFactory {
            RecipeOwnershipViewModel(
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

    private val horizontalPagerViewModel by viewModels<HorizontalPagerViewModel> {
        GenericViewModelFactory {
            HorizontalPagerViewModel(
                GetApplicationUserFavoriteRecipesUseCase(
                    ApplicationUserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager)
                            .culinaryChestApi
                    )
                ),
                GetApplicationUserRecipesUseCase(
                    ApplicationUserRecipeRepositoryImpl(
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
                    ApplicationUserRepositoryImpl(RetrofitInstance(tokenManager).culinaryChestApi)
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

    private val recipeByIdViewModel by viewModels<RecipeByIdViewModel> {
        GenericViewModelFactory {
            RecipeByIdViewModel(
                GetRecipeByIdUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val createRecipeViewModel by viewModels<CreateRecipeViewModel> {
        GenericViewModelFactory {
            CreateRecipeViewModel(
                CreateApplicationUserRecipeUseCase(
                    ApplicationUserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManager).culinaryChestApi
                    )
                )
            )
        }
    }

    private val manageRecipeViewModel by viewModels<ManageRecipeViewModel> {
        GenericViewModelFactory {
            ManageRecipeViewModel(
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

    private val favoriteRecipeByRecipeIdViewModel by viewModels<FavoriteRecipeByRecipeIdViewModel> {
        GenericViewModelFactory {
            FavoriteRecipeByRecipeIdViewModel(
                GetFavoriteRecipeByRecipeIdUseCase(
                    ApplicationUserFavoriteRecipeRepositoryImpl(RetrofitInstance(tokenManager)
                        .culinaryChestApi
                    )
                )
            )
        }
    }

    private val manageFavoriteRecipeViewModel by viewModels<ManageFavoriteRecipeViewModel> {
        GenericViewModelFactory {
            ManageFavoriteRecipeViewModel(
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
                    recipeOwnershipViewModel,
                    horizontalPagerViewModel,
                    profileViewModel,
                    recipeByIdViewModel,
                    createRecipeViewModel,
                    manageRecipeViewModel,
                    manageFavoriteRecipeViewModel,
                    favoriteRecipeByRecipeIdViewModel,
                    manageStepsViewModel,
                    tokenManager
                )
            }
        }
    }
}

