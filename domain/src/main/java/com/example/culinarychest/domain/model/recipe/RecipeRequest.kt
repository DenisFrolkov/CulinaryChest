package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.application_user.Token

data class RecipeRequest(
    val token: Token,
    val recipeId: String
)