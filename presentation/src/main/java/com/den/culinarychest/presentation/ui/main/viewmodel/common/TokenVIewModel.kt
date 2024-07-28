package com.den.culinarychest.presentation.ui.main.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.usecase.tokenUseCase.ClearTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.SaveTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TokenViewModel(
    private val saveTokenUseCase: SaveTokenUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase,
) : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    init {
        getToken()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            saveTokenUseCase(token)
            _token.value = token
        }
    }

    fun getToken() {
        viewModelScope.launch {
            val token = getTokenUseCase()
            _token.value = token
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            clearTokenUseCase()
            _token.value = null // Обновляем состояние после очистки токена
        }
    }
}