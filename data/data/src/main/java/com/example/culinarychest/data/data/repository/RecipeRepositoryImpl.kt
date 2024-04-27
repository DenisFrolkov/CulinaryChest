package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return flow {
            safeApiCall {
                culinaryChestAPI.getRecipes(token = token)
            }
        }
    }
    override suspend fun getRecipeById(recipeId: String): ProcessingResult<Recipe> {
        return safeApiCall {
            culinaryChestAPI.getRecipeById(recipeId)
        }
    }

}