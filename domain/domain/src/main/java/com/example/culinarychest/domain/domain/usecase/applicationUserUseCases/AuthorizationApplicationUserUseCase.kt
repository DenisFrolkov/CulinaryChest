package com.example.culinarychest.domain.domain.usecase.applicationUserUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.model.application_user.Token
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.flow.Flow

class AuthorizationApplicationUserUseCase(
    private val repository: ApplicationUserRepository
) {
    suspend operator fun invoke(login: Login): Flow<ProcessingResult<Token>> {
        return repository.authorizationApplicationUser(login)
    }
}