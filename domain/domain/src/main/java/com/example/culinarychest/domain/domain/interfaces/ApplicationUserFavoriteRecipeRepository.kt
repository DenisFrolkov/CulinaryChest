package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.CreateFavoriteRecipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserFavoriteRecipeRepository {

    suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>
    suspend fun createApplicationUserFavoriteRecipes(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe)
    suspend fun deleteApplicationUserFavoriteRecipe(token: String, favoriteRecipeId: String)

}