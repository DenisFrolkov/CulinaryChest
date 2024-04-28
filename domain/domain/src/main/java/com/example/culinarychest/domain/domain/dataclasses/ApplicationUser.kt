package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName

data class ApplicationUser(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Roles")
    val roles: List<String>
)