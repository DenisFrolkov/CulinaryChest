package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.model.ApplicationUser
import com.example.culinarychest.domain.domain.model.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface ApplicationUserRepository {
    suspend fun registrationApplicationUser(user: ApplicationUser)
    suspend fun authorizationApplicationUser(username: String, password: String): ProcessingResult<Token>
    suspend fun getApplicationUserId(token: String): Flow<ProcessingResult<ApplicationUserInfo>>
}
