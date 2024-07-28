package com.den.culinarychest.presentation.ui.main.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.GetUserRecipesUseCase
import com.example.culinarychest.domain.usecase.userUseCases.GetUserInfoUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
    private val getUserFavoriteRecipesUseCase: GetUserFavoriteRecipesUseCase
) : ViewModel() {

    private val _userInfoResult = MutableStateFlow<UserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _numberRecipesApplicationUser = MutableStateFlow<Int?>(null)
    val numberRecipesApplicationUser = _numberRecipesApplicationUser.asStateFlow()

    private val _numberFavoriteRecipesApplicationUser = MutableStateFlow<Int?>(null)
    val numberFavoriteRecipesApplicationUser = _numberFavoriteRecipesApplicationUser.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            getToken?.let {
                getUserInfoUseCase(Token(token = it))
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
                            is ProcessingResult.Loading -> {

                            }
                        }
                    }
            }
        }
    }

    fun getUserRecipesCount() {
        viewModelScope.launch {
            getToken?.let {
                getUserRecipesUseCase(Token(token = it))
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
                            is ProcessingResult.Loading -> {

                            }
                        }

                    }
            }
        }
    }

    fun getUserFavoriteRecipesCount() {
        viewModelScope.launch {
            getToken?.let {
                getUserFavoriteRecipesUseCase(Token(it))
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
                            is ProcessingResult.Loading -> {

                            }
                        }
                    }
            }
        }
    }

}