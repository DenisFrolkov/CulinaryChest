package com.example.culinarychest.data.model.application_user

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Roles")
    val roles: List<String>
)