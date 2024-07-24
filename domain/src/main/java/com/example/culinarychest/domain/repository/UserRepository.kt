package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.model.application_user.Token
import retrofit2.Response

interface UserRepository {
    suspend fun registrationApplicationUser(user: ApplicationUser) : Response<DuplicationUserInfo>
    suspend fun authorizationApplicationUser(login: Login): Response<Token>
    suspend fun getApplicationUserInfo(token: String): UserInfo
}
