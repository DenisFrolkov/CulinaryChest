package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRecipeRepository
import kotlinx.coroutines.flow.Flow

class ApplicationUserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserRecipes(token)
        }
    }
    override suspend fun createApplicationUserRecipe(token: String, recipe: CreateRecipe) {
        culinaryChestAPI.createApplicationUserRecipe(token, recipe)
    }
    override suspend fun updateApplicationUserRecipe(): Flow<ProcessingResult<Recipe>> {
        return safeApiCall {
            culinaryChestAPI.updateApplicationUserRecipe()
        }
    }
    override suspend fun deleteApplicationUserRecipe(): Flow<ProcessingResult<Recipe>> {
        return safeApiCall {
            culinaryChestAPI.deleteApplicationUserRecipe()
        }
    }

}