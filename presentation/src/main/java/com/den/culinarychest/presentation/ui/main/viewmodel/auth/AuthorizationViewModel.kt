package com.den.culinarychest.presentation.ui.main.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.usecase.tokenUseCase.SaveTokenUseCase
import com.example.culinarychest.domain.usecase.userUseCases.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<ProcessingResult<Boolean>>(ProcessingResult.Success(false))
    val authState: StateFlow<ProcessingResult<Boolean>> = _authState

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    fun authorizationUser(login: Login) {
        viewModelScope.launch {
            userUseCase(login).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {

                    }
                    is ProcessingResult.Success -> {
                        val token = result.data?.token.orEmpty()
                        if (token.isNotBlank()) {
                            saveTokenUseCase(token)
                            _token.value = token
                            _authState.value = ProcessingResult.Success(true)
                        } else {
                            _authState.value = ProcessingResult.Error("Invalid token")
                        }
                    }
                }
            }
        }
    }

    fun clearState() {
        _authState.value = ProcessingResult.Success(false)
        _token.value = null
    }
}