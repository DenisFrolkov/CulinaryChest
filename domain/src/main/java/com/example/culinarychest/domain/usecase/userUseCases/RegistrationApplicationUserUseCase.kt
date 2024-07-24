package com.example.culinarychest.domain.usecase.userUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationApplicationUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: ApplicationUser): Flow<ProcessingResult<DuplicationUserInfo>> =
        flow {
            try {
                val response =
                    repository.registrationApplicationUser(user)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(ProcessingResult.Success(body))
                    } else {
                        emit(ProcessingResult.Error("Empty response body"))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    emit(ProcessingResult.Error(errorBody ?: "Unknown error"))
                }
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }

}