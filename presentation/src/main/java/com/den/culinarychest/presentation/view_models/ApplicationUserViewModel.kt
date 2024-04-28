package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUserInfo
import com.example.culinarychest.domain.domain.dataclasses.Token
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRepository
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationUserViewModel(
    private val applicationUserRepository: ApplicationUserRepository,
) : ViewModel() {

    private val _registrationResult = MutableLiveData<ProcessingResult<ApplicationUser>>()
    val registrationResult: LiveData<ProcessingResult<ApplicationUser>> = _registrationResult

    private val _token = MutableLiveData<String?>(null)
    val token get() = _token

    private val _userInfoResult = MutableStateFlow<ApplicationUserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        token.observeForever() { token ->
            if (token != null) {
                getApplicationUserInfo(token = token)
            }
        }
    }

    fun registerApplicationUser(
        username: String,
        email: String,
        password: String,
        roles: List<String>
    ) {
        viewModelScope.launch {
            try {
                applicationUserRepository.registrationApplicationUser(
                    username,
                    email,
                    password,
                    roles
                )
                _registrationResult.value = ProcessingResult.Success(null)
            } catch (e: Exception) {
                _registrationResult.value =
                    ProcessingResult.Error(message = "Error registering user: ${e.message}")
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun authorizeUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result =
                    applicationUserRepository.authorizationApplicationUser(username, password)
                if (result is ProcessingResult.Success) {
                    setToken("Bearer ${result.data?.token ?: ""}")
                } else {
                    _showErrorToastChannel.send(true)
                }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun getApplicationUserInfo(token: String) {
        viewModelScope.launch {
            applicationUserRepository.getApplicationUserId(token = token).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is ProcessingResult.Success -> {
                        result.data.let { userInfo ->
                            _userInfoResult.update { userInfo }
                        }

                    }
                }
            }
        }
    }

    private fun setToken(token: String) {
        _token.value = token
    }
}




