package com.example.culinarychest.domain.domain.model.recipe

import com.example.culinarychest.domain.domain.model.step.CreateStep
import java.io.File

data class CreateRecipe(
    val recipeImage: File,
    val title: String,
    val ingredients: String,
    val steps: List<CreateStep>,
    val creationDate: String,
    val preparationTime: String
)