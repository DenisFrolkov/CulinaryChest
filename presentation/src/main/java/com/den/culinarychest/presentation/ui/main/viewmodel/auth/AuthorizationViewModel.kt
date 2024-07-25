package com.den.culinarychest.presentation.ui.main.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.data.repository.TokenRepositoryImpl
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.SaveTokenUseCase
import com.example.culinarychest.domain.usecase.userUseCases.UserUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<ProcessingResult<Boolean>>()
    val authState: LiveData<ProcessingResult<Boolean>> = _authState


    fun authorizationUser(login: Login) {
        viewModelScope.launch {
            userUseCase(login)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _authState.value = ProcessingResult.Error("Authorization failed")
                        }
                        is ProcessingResult.Success -> {
                            val token = result.data?.token.orEmpty()
                            saveTokenUseCase(token)
                            _authState.value = ProcessingResult.Success(token.isNotBlank())
                        }
                    }
                }
        }
    }

}