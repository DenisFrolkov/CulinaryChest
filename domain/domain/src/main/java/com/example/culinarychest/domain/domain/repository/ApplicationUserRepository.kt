package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Token
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Response

interface ApplicationUserRepository {
    suspend fun registrationApplicationUser(user: ApplicationUser) : Response<DuplicationUserInfo>
    suspend fun authorizationApplicationUser(username: String, password: String): ProcessingResult<Token>
    suspend fun getApplicationUserId(token: String): Flow<ProcessingResult<ApplicationUserInfo>>
}
