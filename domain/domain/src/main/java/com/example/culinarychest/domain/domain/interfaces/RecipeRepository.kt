package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe

interface RecipeRepository {

    suspend fun getRecipes(): ProcessingResult<List<Recipe>>
    suspend fun getRecipeById(recipeId: String): ProcessingResult<Recipe>

}