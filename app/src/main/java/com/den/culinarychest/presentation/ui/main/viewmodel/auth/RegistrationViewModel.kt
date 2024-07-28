package com.den.culinarychest.presentation.ui.main.viewmodel.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.usecase.userUseCases.RegistrationUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegistrationViewModel(
    private val registrationUserUseCase: RegistrationUserUseCase,
) : ViewModel() {

    private val _duplicationUserInfo = MutableStateFlow<DuplicationUserInfo?>(null)
    val duplicationUserInfo = _duplicationUserInfo.asStateFlow()

    private val _regState = MutableStateFlow<ProcessingResult<Boolean>>(ProcessingResult.Success(false))
    val regState: StateFlow<ProcessingResult<Boolean>> = _regState

    fun registrationUser(user: ApplicationUser) {
        viewModelScope.launch {
            registrationUserUseCase(user).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        val errorBody = result.message
                        val duplicationInfo = parseErrorMessage(errorBody)
                        if (duplicationInfo != null) {
                            _duplicationUserInfo.value = DuplicationUserInfo(
                                duplicationInfo.duplicateUserName,
                                duplicationInfo.duplicateEmail
                            )
                            _regState.value = ProcessingResult.Error("Registration failed", true)
                        } else _regState.value = ProcessingResult.Success(true)
                    }
                    is ProcessingResult.Success -> {

                    }
                    is ProcessingResult.Loading -> {
                        _regState.value = ProcessingResult.Loading()
                    }
                }
            }
        }
    }

    private fun parseErrorMessage(errorBody: String?): DuplicationUserInfo? {
        if (errorBody.isNullOrBlank()) return null

        return runCatching {
            val jsonObject = JSONObject(errorBody)
            val duplicateEmailMessage = jsonObject.optJSONArray("DuplicateEmail")?.optString(0)
            val duplicateUserNameMessage = jsonObject.optJSONArray("DuplicateUserName")?.optString(0)

            DuplicationUserInfo(
                duplicateUserName = duplicateUserNameMessage,
                duplicateEmail = duplicateEmailMessage
            )
        }.getOrElse {
            null
        }
    }

    fun clearState() {
        _duplicationUserInfo.value = null
        _regState.value = ProcessingResult.Success(false)
    }


}