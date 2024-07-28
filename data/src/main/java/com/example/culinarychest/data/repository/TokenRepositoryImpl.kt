package com.example.culinarychest.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.culinarychest.domain.repository.TokenRepository

private const val SHARED_PREFS_AUTH = "shared_prefs_auth"
private const val SHARED_PREFS_TOKEN = "shared_prefs_token"

class TokenRepositoryImpl(private val context: Context) : TokenRepository {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFS_AUTH, Context.MODE_PRIVATE)
    }

    override fun saveToken(token: String) {
        return sharedPreferences.edit().putString(SHARED_PREFS_TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(SHARED_PREFS_TOKEN, null)
    }

    override fun clearToken() {
        return sharedPreferences.edit().remove(SHARED_PREFS_TOKEN).apply()
    }
}