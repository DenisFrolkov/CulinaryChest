package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.CreateFavoriteRecipe
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

    override suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe) {
        culinaryChestAPI.createApplicationUserFavoriteRecipes(token, recipeId, addedDate)
    }

    override suspend fun deleteApplicationUserFavoriteRecipe(): Flow<ProcessingResult<FavoriteRecipe>> {
        return safeApiCall {
            culinaryChestAPI.deleteApplicationUserFavoriteRecipe()
        }
    }

}