package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.application_user.Token

data class RecipeIdsRequest(
    val token: Token,
    val recipeIds: List<String>
)
