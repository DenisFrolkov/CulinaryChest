package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeIdsRequest
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.recipe.SearchRequest
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(searchRequest: SearchRequest): List<Recipe>
    suspend fun getRecipesByIds(recipeIdsRequest: RecipeIdsRequest): List<Recipe>
    suspend fun getRecipeById(recipeRequest: RecipeRequest): List<Recipe>
}