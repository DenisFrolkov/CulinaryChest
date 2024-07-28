package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.application_user.Token
import java.io.File

data class UpdateRecipe(
    val token: Token,
    val recipeId: String,
    val title: String,
    val recipeImage: File? = null,
    val ingredients: String,
    val creationDate: String,
    val preparationTime: String,
    val savedCount: Int,
)