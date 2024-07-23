package com.example.culinarychest.domain.usecase.applicationUserUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.flow.Flow

class RegistrationApplicationUserUseCase(
    private val repository: ApplicationUserRepository
) {
    suspend operator fun invoke(user: ApplicationUser): Flow<ProcessingResult<DuplicationUserInfo>> {
        return repository.registrationApplicationUser(user)
    }
}