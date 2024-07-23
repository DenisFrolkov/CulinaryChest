package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserFavoriteRecipeRepository {
    suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun getFavoriteRecipeByRecipeId(token: String, recipeId: String): Flow<ProcessingResult<FavoriteRecipe>>
    suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe)
    suspend fun deleteApplicationUserFavoriteRecipe(token: String, recipeId: String)
}