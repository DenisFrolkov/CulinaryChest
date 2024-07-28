package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.application_user.Token
import java.io.File

data class CreateRecipe(
    val token: Token,
    val recipeImage: File,
    val title: String,
    val ingredients: String,
    val steps: List<String>,
    val creationDate: String,
    val preparationTime: String
)