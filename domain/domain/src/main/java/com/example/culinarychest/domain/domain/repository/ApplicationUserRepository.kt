package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.model.application_user.Token
import kotlinx.coroutines.flow.Flow

interface ApplicationUserRepository {
    suspend fun registrationApplicationUser(user: ApplicationUser) : Flow<ProcessingResult<DuplicationUserInfo>>
    suspend fun authorizationApplicationUser(login: Login): Flow<ProcessingResult<Token>>
    suspend fun getApplicationUserInfo(token: String): Flow<ProcessingResult<ApplicationUserInfo>>
}
