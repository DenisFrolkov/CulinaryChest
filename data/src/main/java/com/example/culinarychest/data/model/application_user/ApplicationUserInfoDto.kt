package com.example.culinarychest.data.model.application_user

import com.google.gson.annotations.SerializedName

data class ApplicationUserInfoDto(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
)
