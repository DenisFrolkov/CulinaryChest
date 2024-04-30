package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.BodyRequest
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class ApplicationUserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserFavoriteRecipes(token = token)
        }
    }

    override suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, bodyRequest: BodyRequest) {
        culinaryChestAPI.createApplicationUserFavoriteRecipes(token, recipeId, bodyRequest)
    }

    override suspend fun deleteApplicationUserFavoriteRecipe(): Flow<ProcessingResult<FavoriteRecipe>> {
        return safeApiCall {
            culinaryChestAPI.deleteApplicationUserFavoriteRecipe()
        }
    }

}