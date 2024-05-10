package com.example.culinarychest.domain.domain.model.application_user

import com.google.gson.annotations.SerializedName

data class ApplicationUserInfo(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
)
