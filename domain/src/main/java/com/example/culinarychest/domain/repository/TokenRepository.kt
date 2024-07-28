package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.application_user.Token

interface TokenRepository {

    fun saveToken(token: Token)
    fun getToken(): String?
    fun clearToken()

}