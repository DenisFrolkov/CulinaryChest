package com.example.culinarychest.domain.model.recipe

import com.example.culinarychest.domain.model.step.Step

data class Recipe(
    val recipeId: String,
    val id: String,
    val imageUrl: String,
    val title: String,
    val ingredients: String,
    val steps: List<Step>,
    val creationDate: String,
    val preparationTime: String,
    val savedCount: Int
)
