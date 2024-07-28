package com.den.culinarychest.presentation.ui.main

import android.os.Bundle
import android.util.Log
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
import com.example.culinarychest.domain.usecase.userUseCases.GetUserInfoUseCase
import com.example.culinarychest.domain.usecase.userUseCases.RegistrationUserUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.UpdateStepUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.ClearTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.SaveTokenUseCase
import com.example.culinarychest.domain.usecase.userUseCases.AuthorizationUserUseCase

class MainActivity : ComponentActivity() {

    init {
        AppModule.init(this)
    }

    private val tokenViewModel by viewModels<TokenViewModel> {
        GenericViewModelFactory {
            TokenViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                ClearTokenUseCase(AppModule.provideTokenRepository())
            )
        }
    }

    private val registrationApplicationUserViewModel by viewModels<RegistrationViewModel> {
        GenericViewModelFactory {
            RegistrationViewModel(
                RegistrationUserUseCase(AppModule.provideUserRepository())
            )
        }
    }

    private val authorizationUserViewModel by viewModels<AuthorizationViewModel> {
        GenericViewModelFactory {
            AuthorizationViewModel(
                SaveTokenUseCase(AppModule.provideTokenRepository()),
                AuthorizationUserUseCase(AppModule.provideUserRepository())
            )
        }
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        GenericViewModelFactory {
            SearchViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                GetRecipesUseCase(AppModule.provideRecipeRepository())
            )
        }
    }

    private val recipeDetailsViewModel by viewModels<RecipeDetailsViewModel> {
        GenericViewModelFactory {
            RecipeDetailsViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                GetFavoriteRecipeByRecipeIdUseCase(AppModule.provideUserFavoriteRecipeRepository()),
                GetRecipeByIdUseCase(AppModule.provideRecipeRepository())
            )
        }
    }

    private val createdViewModel by viewModels<CreatedViewModel> {
        GenericViewModelFactory {
            CreatedViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                GetUserRecipesUseCase(AppModule.provideUserRecipeRepository())
            )
        }
    }

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        GenericViewModelFactory {
            FavoriteViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                GetUserFavoriteRecipesUseCase(AppModule.provideUserFavoriteRecipeRepository()),
                GetRecipesByIdsUseCase(AppModule.provideRecipeRepository())
            )
        }
    }

    private val profileViewModel by viewModels<ProfileViewModel> {
        GenericViewModelFactory {
            ProfileViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                GetUserInfoUseCase(AppModule.provideUserRepository()),
                GetUserRecipesUseCase(AppModule.provideUserRecipeRepository()),
                GetUserFavoriteRecipesUseCase(AppModule.provideUserFavoriteRecipeRepository())
            )
        }
    }

    private val creatingRecipeViewModel by viewModels<CreatingRecipeViewModel> {
        GenericViewModelFactory {
            CreatingRecipeViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                CreateUserRecipeUseCase(AppModule.provideUserRecipeRepository())
            )
        }
    }

    private val manageRecipeUserViewModel by viewModels<ManageRecipeUserViewModel> {
        GenericViewModelFactory {
            ManageRecipeUserViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                UpdateUserRecipeUseCase(AppModule.provideUserRecipeRepository()),
                DeleteUserRecipeUseCase(AppModule.provideUserRecipeRepository())
            )
        }
    }

    private val manageOtherRecipeViewModel by viewModels<ManageOtherRecipeViewModel> {
        GenericViewModelFactory {
            ManageOtherRecipeViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                CreateApplicationUserFavoriteRecipesUseCase(AppModule.provideUserFavoriteRecipeRepository()),
                DeleteApplicationUserFavoriteRecipeUseCase(AppModule.provideUserFavoriteRecipeRepository())
            )
        }
    }

    private val manageStepsViewModel by viewModels<ManageStepsViewModel> {
        GenericViewModelFactory {
            ManageStepsViewModel(
                GetTokenUseCase(AppModule.provideTokenRepository()),
                CreateStepUseCase(AppModule.provideRecipeStepsRepository()),
                DeleteStepUseCase(AppModule.provideRecipeStepsRepository()),
                UpdateStepUseCase(AppModule.provideRecipeStepsRepository())
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.e("AAA", "Activity created")
            CulinaryChestTheme {
                AppNavigation(
                    tokenViewModel,
                    registrationApplicationUserViewModel,
                    authorizationUserViewModel,
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