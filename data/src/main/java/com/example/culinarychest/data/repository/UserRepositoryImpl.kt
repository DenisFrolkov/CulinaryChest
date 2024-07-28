package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.data.model.Mappers.toDto
import com.example.culinarychest.data.model.application_user.DuplicationUserInfoDto
import com.example.culinarychest.data.model.application_user.TokenDto
import com.example.culinarychest.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.model.application_user.UserInfo
import com.example.culinarychest.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.model.application_user.Login
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : UserRepository {
    override suspend fun registrationUser(user: ApplicationUser): Response<DuplicationUserInfo> {
        val response = culinaryChestAPI.registrationUser(user.toDto())

        return if (response.isSuccessful) {
            val domainResponse = response.body()?.toDomain()
            Response.success(domainResponse)
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }

    override suspend fun authorizationApplicationUser(login: Login): Response<Token> {

        val response = culinaryChestAPI.authorizationUser(login.toDto())

        val domainResponse = response.body()?.toDomain()

        return Response.success(domainResponse)

    }


    override suspend fun getApplicationUserInfo(token: String): UserInfo {
        return culinaryChestAPI.getUserInfo(token).toDomain()
    }
}

