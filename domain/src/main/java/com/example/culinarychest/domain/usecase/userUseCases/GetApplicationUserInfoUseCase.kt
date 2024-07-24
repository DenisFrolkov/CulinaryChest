package com.example.culinarychest.domain.usecase.userUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.repository.UserRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetApplicationUserInfoUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<UserInfo>> = flow {
        emit(safeApiCall { repository.getApplicationUserInfo(token) })
    }
}