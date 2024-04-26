package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRecipeRepository

class ApplicationUserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getApplicationUserRecipes(): ProcessingResult<List<Recipe>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserRecipes()
        }
    }
    override suspend fun createApplicationUserRecipe(): ProcessingResult<List<Recipe>> {
        return safeApiCall {
            culinaryChestAPI.createApplicationUserRecipe()
        }
    }
    override suspend fun updateApplicationUserRecipe(): ProcessingResult<Recipe> {
        return safeApiCall {
            culinaryChestAPI.updateApplicationUserRecipe()
        }
    }
    override suspend fun deleteApplicationUserRecipe(): ProcessingResult<Recipe> {
        return safeApiCall {
            culinaryChestAPI.deleteApplicationUserRecipe()
        }
    }

}