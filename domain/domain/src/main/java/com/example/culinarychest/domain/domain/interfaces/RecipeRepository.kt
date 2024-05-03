package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun getRecipeById(token: String, recipeId: String): Flow<ProcessingResult<List<Recipe>>>
}