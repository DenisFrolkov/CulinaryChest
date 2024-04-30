package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.BodyRequest
import kotlinx.coroutines.flow.Flow

interface ApplicationUserFavoriteRecipeRepository {

    suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, bodyRequest: BodyRequest)
    suspend fun deleteApplicationUserFavoriteRecipe(): Flow<ProcessingResult<FavoriteRecipe>>

}