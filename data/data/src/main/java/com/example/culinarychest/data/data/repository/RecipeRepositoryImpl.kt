package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(): ProcessingResult<List<Recipe>> {
        return safeApiCall {
            culinaryChestAPI.getRecipes()
        }
    }
    override suspend fun getRecipeById(recipeId: String): ProcessingResult<Recipe> {
        return safeApiCall {
            culinaryChestAPI.getRecipeById(recipeId)
        }
    }

}