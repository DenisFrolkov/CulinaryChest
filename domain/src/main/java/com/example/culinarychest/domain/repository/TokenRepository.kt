package com.example.culinarychest.domain.repository

interface TokenRepository {

    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()

}