package com.example.culinarychest.domain.domain.usecase.applicationUserUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationUserInfoUseCase(
    private val repository: ApplicationUserRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<ApplicationUserInfo>>{
        return repository.getApplicationUserInfo(token)
    }
}