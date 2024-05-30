package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserFavoriteRecipeRepository {
    suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun getFavoriteRecipeByRecipeId(token: String, recipeId: String): Flow<ProcessingResult<FavoriteRecipe>>
    suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe)
    suspend fun deleteApplicationUserFavoriteRecipe(token: String, favoriteRecipeId: String)
}