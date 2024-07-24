package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(token: String, searchTerm: String?): List<Recipe>
    suspend fun getRecipesByIds(token: String, recipeIds: List<String>): List<Recipe>
    suspend fun getRecipeById(token: String, recipeId: String): List<Recipe>
}