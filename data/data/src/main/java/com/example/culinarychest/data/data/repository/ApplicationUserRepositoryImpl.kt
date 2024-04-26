package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.Login
import com.example.culinarychest.domain.domain.dataclasses.Token
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRepository

class ApplicationUserRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRepository {

    override suspend fun registrationApplicationUser(
        username: String,
        email: String,
        password: String,
        roles: List<String>
    ) {
        culinaryChestAPI.registrationApplicationUser(
            applicationUser = ApplicationUser(
                userName = username,
                email = email,
                password = password,
                roles = roles
            )
        )
    }

    override suspend fun authorizationApplicationUser(username: String, password: String): ProcessingResult<Token> {
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

    override suspend fun getApplicationUserId(): ProcessingResult<ApplicationUser> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserId()
        }
    }
}

