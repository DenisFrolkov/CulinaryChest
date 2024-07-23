package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(token: String, searchTerm: String?): Flow<ProcessingResult<List<Recipe>>>
    suspend fun getRecipesByIds(token: String, recipeIds: List<String>): Flow<ProcessingResult<List<Recipe>>>
    suspend fun getRecipeById(token: String, recipeId: String): Flow<ProcessingResult<List<Recipe>>>
}