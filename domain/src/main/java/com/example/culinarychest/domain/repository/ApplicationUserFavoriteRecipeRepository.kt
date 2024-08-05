package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipeRequest

interface ApplicationUserFavoriteRecipeRepository {
    suspend fun getUserFavoriteRecipes(token: Token): List<FavoriteRecipe>
    suspend fun getFavoriteRecipeByRecipeId(favoriteRecipeRequest: FavoriteRecipeRequest): FavoriteRecipe
    suspend fun createApplicationUserFavoriteRecipes(createFavoriteRecipe: CreateFavoriteRecipe)
    suspend fun deleteApplicationUserFavoriteRecipe(favoriteRecipeRequest: FavoriteRecipeRequest)
}