package com.example.culinarychest.data.data.repository

import android.content.Context
import android.content.SharedPreferences

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }
}