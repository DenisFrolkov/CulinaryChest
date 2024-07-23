package com.example.culinarychest.domain.model.step

data class Step(
    val stepId: Int,
    val description: String,
    val order: Int,
    val recipeId: Int
)