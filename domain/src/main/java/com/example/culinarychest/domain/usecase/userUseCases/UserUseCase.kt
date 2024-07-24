package com.example.culinarychest.domain.usecase.userUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(login: Login): Flow<ProcessingResult<Token>> =
        flow {
            try {
                val response = repository.authorizationApplicationUser(login)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(ProcessingResult.Success(body))
                    } else {
                        emit(ProcessingResult.Error("Empty response body"))
                    }
                } else {
                    emit(ProcessingResult.Error("HTTP error ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }
}