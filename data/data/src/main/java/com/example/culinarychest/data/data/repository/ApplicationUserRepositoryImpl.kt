package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.data.data.model.application_user.DuplicationUserInfoDto
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUser
import com.example.culinarychest.domain.domain.model.application_user.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.DuplicationUserInfo
import com.example.culinarychest.domain.domain.model.application_user.Login
import com.example.culinarychest.domain.domain.model.application_user.Token
import com.example.culinarychest.domain.domain.repository.ApplicationUserRepository
import com.example.culinarychest.domain.domain.model.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ApplicationUserRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRepository {
    override suspend fun registrationApplicationUser(user: ApplicationUser): Flow<ProcessingResult<DuplicationUserInfo>> {
        return flow {
            try {
                val response: Response<DuplicationUserInfoDto> =
                    culinaryChestAPI.registrationApplicationUser(user.toDto())
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(ProcessingResult.Success(body.toDomain()))
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

    override suspend fun authorizationApplicationUser(login: Login): Flow<ProcessingResult<Token>> {
        return flow {
            try {
                val response = culinaryChestAPI.authorizationApplicationUser(login.toDto())
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(ProcessingResult.Success(body.toDomain()))
                    } else {
                        emit(ProcessingResult.Error("Empty response body"))
                    }
                } else {
                    emit(ProcessingResult.Error("HTTP error ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }
    }


    override suspend fun getApplicationUserInfo(token: String): Flow<ProcessingResult<ApplicationUserInfo>> =
        flow {
            try {
                val response = culinaryChestAPI.getApplicationUserInfo(token).toDomain()
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }
}

