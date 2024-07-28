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
import com.den.culinarychest.presentation.ui.main.viewmodel.common.TokenViewModel
import com.den.culinarychest.presentation.ui.main.viewmodel.recipes.SearchViewModel
import com.example.culinarychest.data.api.RetrofitInstance
import com.example.culinarychest.data.repository.UserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.repository.UserRecipeRepositoryImpl
import com.example.culinarychest.data.repository.UserRepositoryImpl
import com.example.culinarychest.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.repository.RecipeStepsRepositoryImpl
import com.example.culinarychest.data.repository.TokenRepositoryImpl
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.CreateUserRecipeUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.DeleteUserRecipeUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.GetUserRecipesUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.UpdateUserRecipeUseCase
import com.example.culinarychest.domain.usecase.userUseCases.UserUseCase
import com.example.culinarychest.domain.usecase.userUseCases.GetApplicationUserInfoUseCase
import com.example.culinarychest.domain.usecase.userUseCases.RegistrationApplicationUserUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.UpdateStepUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.ClearTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.SaveTokenUseCase

class MainActivity : ComponentActivity() {

    private val tokenManagerImpl = TokenRepositoryImpl(this)

    private val tokenViewModel by viewModels<TokenViewModel> {
        GenericViewModelFactory {
            TokenViewModel(
                SaveTokenUseCase(
                    tokenManagerImpl
                ),
                GetTokenUseCase(
                    tokenManagerImpl
                ),
                ClearTokenUseCase(
                    tokenManagerImpl
                )
            )
        }
    }

    private val registrationApplicationUserViewModel by viewModels<RegistrationViewModel> {
        GenericViewModelFactory {
            RegistrationViewModel(
                RegistrationApplicationUserUseCase(
                    UserRepositoryImpl(
                        RetrofitInstance(
                            tokenManagerImpl
                        ).culinaryChestApi
                    )
                )
            )
        }
    }


    private val authorizationApplicationUserViewModel by viewModels<AuthorizationViewModel> {
        GenericViewModelFactory {
            AuthorizationViewModel(
                SaveTokenUseCase(
                    tokenManagerImpl
                ),
                UserUseCase(
                    UserRepositoryImpl(
                        RetrofitInstance(
                            tokenManagerImpl
                        ).culinaryChestApi
                    )
                )
            )
        }
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        GenericViewModelFactory {
            SearchViewModel(
                GetTokenUseCase(tokenManagerImpl),
                GetRecipesUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl).culinaryChestApi
                    )
                )
            )
        }
    }

    private val recipeDetailsViewModel by viewModels<RecipeDetailsViewModel> {
        GenericViewModelFactory {
            RecipeDetailsViewModel(
                GetTokenUseCase(tokenManagerImpl),
                GetFavoriteRecipeByRecipeIdUseCase(
                    UserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                ),
                GetRecipeByIdUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl).culinaryChestApi
                    )
                )
            )
        }
    }

    private val createdViewModel by viewModels<CreatedViewModel> {
        GenericViewModelFactory {
            CreatedViewModel(
                GetTokenUseCase(tokenManagerImpl),
                GetUserRecipesUseCase(
                    UserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                )
            )
        }
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        GenericViewModelFactory {
            FavoriteViewModel(
                GetTokenUseCase(tokenManagerImpl),
                GetUserFavoriteRecipesUseCase(
                    UserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                ),
                GetRecipesByIdsUseCase(
                    RecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl).culinaryChestApi
                    )
                )
            )
        }
    }

    private val profileViewModel by viewModels<ProfileViewModel> {
        GenericViewModelFactory {
            ProfileViewModel(
                GetTokenUseCase(tokenManagerImpl),
                GetApplicationUserInfoUseCase(
                    UserRepositoryImpl(
                        RetrofitInstance(
                            tokenManagerImpl
                        ).culinaryChestApi
                    )
                ),
                GetUserRecipesUseCase(
                    UserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                ),
                GetUserFavoriteRecipesUseCase(
                    UserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                )
            )
        }
    }

    private val creatingRecipeViewModel by viewModels<CreatingRecipeViewModel> {
        GenericViewModelFactory {
            CreatingRecipeViewModel(
                GetTokenUseCase(tokenManagerImpl),
                CreateUserRecipeUseCase(
                    UserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl).culinaryChestApi
                    )
                )
            )
        }
    }

    private val manageRecipeUserViewModel by viewModels<ManageRecipeUserViewModel> {
        GenericViewModelFactory {
            ManageRecipeUserViewModel(
                GetTokenUseCase(tokenManagerImpl),
                UpdateUserRecipeUseCase(
                    UserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                ),
                DeleteUserRecipeUseCase(
                    UserRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                )
            )
        }
    }

    private val manageOtherRecipeViewModel by viewModels<ManageOtherRecipeViewModel> {
        GenericViewModelFactory {
            ManageOtherRecipeViewModel(
                GetTokenUseCase(tokenManagerImpl),
                CreateApplicationUserFavoriteRecipesUseCase(
                    UserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
                            .culinaryChestApi
                    )
                ),
                DeleteApplicationUserFavoriteRecipeUseCase(
                    UserFavoriteRecipeRepositoryImpl(
                        RetrofitInstance(tokenManagerImpl)
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
                    GetTokenUseCase(tokenManagerImpl),
                    CreateStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManagerImpl).culinaryChestApi
                        )
                    ),
                    DeleteStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManagerImpl).culinaryChestApi
                        )
                    ),
                    UpdateStepUseCase(
                        RecipeStepsRepositoryImpl(
                            RetrofitInstance(tokenManagerImpl).culinaryChestApi
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
                    tokenViewModel,
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
                )
            }
        }
    }
}

