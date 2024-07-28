package com.example.culinarychest.domain.model.step

import com.example.culinarychest.domain.model.application_user.Token

class DeleteStep(
    val token: Token,
    val recipeId: String,
    val stepId: String
)