package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe

interface ApplicationUserFavoriteRecipeRepository {

    suspend fun getApplicationUserFavoriteRecipes(): ProcessingResult<List<FavoriteRecipe>>
    suspend fun createApplicationUserFavoriteRecipes(): ProcessingResult<List<FavoriteRecipe>>
    suspend fun deleteApplicationUserFavoriteRecipe(): ProcessingResult<FavoriteRecipe>

}