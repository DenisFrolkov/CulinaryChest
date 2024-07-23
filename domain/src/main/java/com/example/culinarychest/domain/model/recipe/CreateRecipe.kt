package com.example.culinarychest.domain.model.recipe

import java.io.File

data class CreateRecipe(
    val recipeImage: File?,
    val title: String,
    val ingredients: String,
    val steps: List<String>,
    val creationDate: String,
    val preparationTime: String
)