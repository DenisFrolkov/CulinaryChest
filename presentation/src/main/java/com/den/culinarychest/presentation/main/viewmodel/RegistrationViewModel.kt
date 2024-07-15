package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.RegistrationApplicationUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegistrationViewModel(
    private val registrationApplicationUserUseCase: RegistrationApplicationUserUseCase,
) : ViewModel() {

    private val _duplicationUserInfo = MutableStateFlow<DuplicationUserInfo?>(null)
    val duplicationUserInfo = _duplicationUserInfo.asStateFlow()

    fun registrationApplicationUser(user: ApplicationUser) {
        viewModelScope.launch {
            registrationApplicationUserUseCase(user)
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


}