package com.example.culinarychest.domain.domain.model.recipe

data class UpdateRecipe(
    val title: String,
    val ingredients: String,
    val creationDate: String,
    val preparationTime: String,
    val savedCount: Int
)