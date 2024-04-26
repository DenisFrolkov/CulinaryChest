package com.example.culinarychest.domain.domain.dataclasses

data class Step(
    val description: String,
    val order: Int,
    val recipeId: Int,
    val stepId: Int
)