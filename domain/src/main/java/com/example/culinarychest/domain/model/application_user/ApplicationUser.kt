package com.example.culinarychest.domain.model.application_user

data class ApplicationUser(
    val userName: String,
    val email: String,
    val password: String,
    val roles: List<String>
)