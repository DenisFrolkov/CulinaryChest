package com.den.culinarychest.presentation.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.data.data.repository.TokenManager
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

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
            try {
                val response = applicationUserRepository.registrationApplicationUser(user)
                if (response.isSuccessful) {
                    _duplicationUserInfo.value = null
                } else {
                    val errorBody = response.errorBody()?.string()
                    val duplicationInfo = parseErrorMessage(errorBody)
                    _duplicationUserInfo.value = duplicationInfo
                }
            } catch (e: Exception) {
                _duplicationUserInfo.value = DuplicationUserInfo(
                    duplicateUserName = null,
                    duplicateEmail = null
                )
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

    fun authorizeUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = applicationUserRepository.authorizationApplicationUser(username, password)
                if (result is ProcessingResult.Success) {
                    val token = result.data?.token.orEmpty()
                    tokenManager.saveToken(token)
                    _authState.value = ProcessingResult.Success(token)
                } else {
                    _authState.value = ProcessingResult.Error("Authorization failed")
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> when (e.code()) {
                        401 -> "Incorrect username or password"
                        else -> "An error occurred during authorization. We'll fix it soon."
                    }
                    else -> "An error occurred during authorization. We'll fix it soon."
                }
                _authState.value = ProcessingResult.Error(errorMessage)
            }
        }
    }

    fun getApplicationUserInfo(token: String) {
        viewModelScope.launch {
            applicationUserRepository.getApplicationUserId(token = token).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {

                    }
                    is ProcessingResult.Success -> {
                        result.data.let { userInfo ->
                            _userInfoResult.update { userInfo }
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
}






