package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.Token
import retrofit2.Response

interface ApplicationUserRepository {
    suspend fun registrationApplicationUser(username: String, email: String, password: String, roles: List<String>)
    suspend fun authorizationApplicationUser(username: String, password: String): Response<Token>
    suspend fun getApplicationUserId(): ProcessingResult<ApplicationUser>
}
