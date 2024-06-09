package com.example.culinarychest.domain.domain.model.recipe

import java.io.File

data class UpdateRecipe(
    val title: String,
    val recipeImage: File? = null,
    val ingredients: String,
    val creationDate: String,
    val preparationTime: String,
    val savedCount: Int
)