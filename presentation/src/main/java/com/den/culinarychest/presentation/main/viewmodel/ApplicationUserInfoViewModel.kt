package com.den.culinarychest.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.usecase.applicationUserUseCases.GetApplicationUserInfoUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationUserInfoViewModel(
    private val getApplicationUserInfoUseCase: GetApplicationUserInfoUseCase
) : ViewModel() {

    private val _userInfoResult = MutableStateFlow<ApplicationUserInfo?>(null)
    val userInfoResult = _userInfoResult.asStateFlow()

    private val _showErrorToastChannel = Channel<String>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getApplicationUserInfo(token: String) {
        viewModelScope.launch {
            getApplicationUserInfoUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            Log.d("ApplicationUser", "Unsuccessful retrieval of user information")
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
}





