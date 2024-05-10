package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.model.application_user.Token
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import kotlinx.coroutines.flow.Flow

class ApplicationUserRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRepository {

    override suspend fun registrationApplicationUser(
        user: ApplicationUser
    ) {
        culinaryChestAPI.registrationApplicationUser(
            applicationUser = user
        )
    }

    override suspend fun authorizationApplicationUser(
        username: String,
        password: String
    ): ProcessingResult<Token> {
        return try {
            val response = culinaryChestAPI.authorizationApplicationUser(Login(username, password))
            if (response.isSuccessful) {
                val token = response.body()
                if (token != null) {
                    ProcessingResult.Success(token)
                } else {
                    ProcessingResult.Error(message = "Token is null")
                }
            } else {
                ProcessingResult.Error(message = "Failed to authenticate: ${response.message()}")
            }
        } catch (e: Exception) {
            ProcessingResult.Error(message = "Error during authentication: ${e.message}")
        }
    }

    override suspend fun getApplicationUserId(token: String): Flow<ProcessingResult<ApplicationUserInfo>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserInfo(token = token)
        }
    }
}

