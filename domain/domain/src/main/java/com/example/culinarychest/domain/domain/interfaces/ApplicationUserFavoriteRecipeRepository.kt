package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserFavoriteRecipeRepository {

    suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun createApplicationUserFavoriteRecipes(): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun deleteApplicationUserFavoriteRecipe(): Flow<ProcessingResult<FavoriteRecipe>>

}