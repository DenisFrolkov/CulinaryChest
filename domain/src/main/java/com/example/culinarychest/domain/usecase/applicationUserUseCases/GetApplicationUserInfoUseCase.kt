package com.example.culinarychest.domain.usecase.applicationUserUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationUserInfoUseCase(
    private val repository: ApplicationUserRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<ApplicationUserInfo>>{
        return repository.getApplicationUserInfo(token)
    }
}