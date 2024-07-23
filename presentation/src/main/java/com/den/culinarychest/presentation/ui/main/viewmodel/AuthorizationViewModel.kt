package com.den.culinarychest.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.AuthorizationApplicationUserUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val tokenManager: TokenManager,
    private val authorizationApplicationUserUseCase: AuthorizationApplicationUserUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<ProcessingResult<Boolean>>()
    val authState: LiveData<ProcessingResult<Boolean>> = _authState

    fun authorizationUser(login: Login) {
        viewModelScope.launch {
            authorizationApplicationUserUseCase(login)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _authState.value = ProcessingResult.Error("Authorization failed")
                        }
                        is ProcessingResult.Success -> {
                            val token = result.data?.token.orEmpty()
                            tokenManager.saveToken(token)
                            _authState.value = ProcessingResult.Success(token.isNotBlank())
                        }
                    }
                }
        }
    }

}