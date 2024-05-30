package com.example.culinarychest.data.data.model.application_user

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Password")
    val password: String
)
