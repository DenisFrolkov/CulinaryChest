package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.flow.Flow

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return safeApiCall {
            culinaryChestAPI.getRecipes(token)
        }
    }
    override suspend fun getRecipeById(token: String, recipeId: String): Flow<ProcessingResult<List<Recipe>>> {
        return safeApiCall {
            culinaryChestAPI.getRecipeById(token, recipeId)
        }
    }
}