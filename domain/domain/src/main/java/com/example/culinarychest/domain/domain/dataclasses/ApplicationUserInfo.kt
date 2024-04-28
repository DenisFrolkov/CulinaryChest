package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName

data class ApplicationUserInfo(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("email")
    val email: String,
)
