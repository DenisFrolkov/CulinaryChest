package com.den.culinarychest.presentation.main.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import com.example.culinarychest.domain.domain.model.ProcessingResult
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

class ApplicationUserViewModel(
    private val tokenManager: TokenManager,
    private val applicationUserRepository: ApplicationUserRepository
) : ViewModel() {

    private val _duplicationUserInfo = MutableStateFlow<DuplicationUserInfo?>(null)
    val duplicationUserInfo = _duplicationUserInfo.asStateFlow()

    private val _authState = MutableLiveData<ProcessingResult<String>>()
    val authState: LiveData<ProcessingResult<String>> = _authState

    private val _userInfoResult = MutableStateFlow<ApplicationUserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _showErrorToastChannel = Channel<String>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun registerApplicationUser(user: ApplicationUser) {
        viewModelScope.launch {
            applicationUserRepository.registrationApplicationUser(user)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            val errorBody = result.message
                            val duplicationInfo = parseErrorMessage(errorBody)
                            _duplicationUserInfo.value = duplicationInfo
                        }
                        is ProcessingResult.Success -> {
                            _duplicationUserInfo.value = null
                        }
                    }
                }
        }
    }

    private fun parseErrorMessage(errorBody: String?): DuplicationUserInfo? {
        return try {
            val jsonObject = JSONObject(errorBody)
            val duplicateEmailMessage = jsonObject.optJSONArray("DuplicateEmail")?.optString(0)
            val duplicateUserNameMessage = jsonObject.optJSONArray("DuplicateUserName")?.optString(0)
            DuplicationUserInfo(
                duplicateUserName = duplicateUserNameMessage,
                duplicateEmail = duplicateEmailMessage
            )
        } catch (e: Exception) {
            null
        }
    }

    fun authorizeUser(login: Login) {
        viewModelScope.launch {
            applicationUserRepository.authorizationApplicationUser(login)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _authState.value = ProcessingResult.Error("Authorization failed")
                        }
                        is ProcessingResult.Success -> {
                            val token = result.data?.token.orEmpty()
                            tokenManager.saveToken(token)
                            _authState.value = ProcessingResult.Success(token)
                        }
                    }
                }
        }
    }

    fun getApplicationUserInfo(token: String) {
        viewModelScope.launch {
            applicationUserRepository.getApplicationUserInfo(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            // Handle error
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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun clear() {
        onCleared()
    }
}





