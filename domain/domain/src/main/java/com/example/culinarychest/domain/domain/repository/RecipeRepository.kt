package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun getRecipesByIds(token: String, recipeIds: List<String>): Flow<ProcessingResult<List<Recipe>>>
}