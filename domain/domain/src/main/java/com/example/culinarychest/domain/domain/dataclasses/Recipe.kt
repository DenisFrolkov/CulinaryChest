package com.example.culinarychest.domain.domain.dataclasses

import com.example.culinarychest.domain.domain.dataclasses.Step

data class Recipe(
    val creationDate: String,
    val id: String,
    val ingredients: String,
    val preparationTime: String,
    val recipeId: Int,
    val recipeImage: String,
    val savedCount: Int,
    val steps: List<Step>,
    val title: String
)