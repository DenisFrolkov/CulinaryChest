package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserFavoriteRecipeRepository

class ApplicationUserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getApplicationUserFavoriteRecipes(): ProcessingResult<List<FavoriteRecipe>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserFavoriteRecipes()
        }
    }

    override suspend fun createApplicationUserFavoriteRecipes(): ProcessingResult<List<FavoriteRecipe>> {
        return safeApiCall {
            culinaryChestAPI.createApplicationUserFavoriteRecipes()
        }
    }

    override suspend fun deleteApplicationUserFavoriteRecipe(): ProcessingResult<FavoriteRecipe> {
        return safeApiCall {
            culinaryChestAPI.deleteApplicationUserFavoriteRecipe()
        }
    }

}