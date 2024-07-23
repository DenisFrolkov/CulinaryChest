package com.den.culinarychest.presentation.ui.main.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.GetApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases.GetApplicationUserRecipesUseCase
import com.example.culinarychest.domain.usecase.applicationUserUseCases.GetApplicationUserInfoUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getApplicationUserInfoUseCase: GetApplicationUserInfoUseCase,
    private val getApplicationUserRecipesUseCase: GetApplicationUserRecipesUseCase,
    private val getApplicationUserFavoriteRecipesUseCase: GetApplicationUserFavoriteRecipesUseCase
) : ViewModel() {

    private val _userInfoResult = MutableStateFlow<ApplicationUserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _numberRecipesApplicationUser = MutableStateFlow<Int?>(null)
    val numberRecipesApplicationUser = _numberRecipesApplicationUser.asStateFlow()

    private val _numberFavoriteRecipesApplicationUser = MutableStateFlow<Int?>(null)
    val numberFavoriteRecipesApplicationUser = _numberFavoriteRecipesApplicationUser.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getUserInfo(token: String) {
        viewModelScope.launch {
            getApplicationUserInfoUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            Log.d("ApplicationUser", "Unsuccessful retrieval of user information")
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { userInfo ->
                                _userInfoResult.update { userInfo }
                            }
                        }
                    }
                }
        }
    }

    fun getUserRecipesCount(token: String) {
        viewModelScope.launch {
            getApplicationUserRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { applicationUserRecipes ->
                                _numberRecipesApplicationUser.update { applicationUserRecipes.size }
                            }
                        }
                    }
                }
        }
    }

    fun getUserFavoriteRecipesCount(token: String) {
        viewModelScope.launch {
            getApplicationUserFavoriteRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipe ->
                                _numberFavoriteRecipesApplicationUser.update { favoriteRecipe.size }
                            }
                        }
                    }
                }
        }
    }

}