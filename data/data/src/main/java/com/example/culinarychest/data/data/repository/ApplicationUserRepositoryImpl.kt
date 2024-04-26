package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.Login
import com.example.culinarychest.domain.domain.dataclasses.Token
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRepository
import retrofit2.Response

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

    override suspend fun authorizationApplicationUser(
        username: String,
        password: String
    ): Response<Token> {
        return culinaryChestAPI.authorizationApplicationUser(
            login = Login(
                userName = username,
                password = password
            )
        )
    }

    override suspend fun getApplicationUserId(): ProcessingResult<ApplicationUser> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserId()
        }
    }
}

