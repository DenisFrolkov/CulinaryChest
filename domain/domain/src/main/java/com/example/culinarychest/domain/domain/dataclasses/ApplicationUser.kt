package com.example.culinarychest.domain.domain.dataclasses

data class ApplicationUser(
    val userName: String,
    val email: String,
    val password: String,
    val roles: List<String>
)