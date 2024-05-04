package com.example.culinarychest.domain.domain.model

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Password")
    val password: String
)
