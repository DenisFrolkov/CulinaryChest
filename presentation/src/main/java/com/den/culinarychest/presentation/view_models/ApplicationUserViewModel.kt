package com.den.culinarychest.presentation.view_models

import android.util.Log
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
import retrofit2.HttpException

class ApplicationUserViewModel(
    private val applicationUserRepository: ApplicationUserRepository
) : ViewModel() {

    private val _registrationResult = MutableLiveData<ProcessingResult<ApplicationUser>>()
    val registrationResult: LiveData<ProcessingResult<ApplicationUser>> = _registrationResult

    private val _token = MutableLiveData<String?>(null)
    val token get() = _token

    private val _userInfoResult = MutableStateFlow<ApplicationUserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _showErrorToastChannel = Channel<String>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    fun registerApplicationUser(
        user: ApplicationUser
    ) {
        viewModelScope.launch {
            try {
                applicationUserRepository.registrationApplicationUser(
                    user = user
                )
                _registrationResult.value = ProcessingResult.Success(null)
            } catch (e: Exception) {
                _registrationResult.value =
                    ProcessingResult.Error(message = "Error registering user: ${e.message}")
//                _showErrorToastChannel.send("Error")
            }
        }
    }

    fun authorizeUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = applicationUserRepository.authorizationApplicationUser(username, password)
                if (result is ProcessingResult.Success) {
                    setToken("Bearer ${result.data?.token ?: ""}")
                } else {
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> when (e.code()) {
                        401 -> "Неправильное имя пользователя или пароль"
                        else -> "Произошла ошибка при авторизации! Но мы скоро все починим ;)"
                    }
                    else -> "Произошла ошибка при авторизации! Но мы скоро все починим ;)"
                }
                _showErrorToastChannel.send(errorMessage)
            }
        }
    }



    fun getApplicationUserInfo(token: String) {
        viewModelScope.launch {
            applicationUserRepository.getApplicationUserId(token = token).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
//                        _showErrorToastChannel.send("Error")
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




