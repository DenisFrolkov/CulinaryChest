package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.data.model.Mappers.toDto
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository

class UserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getUserFavoriteRecipes(token: String): List<FavoriteRecipe> {
        return culinaryChestAPI.getListFavoriteRecipesUser(token).map { it.toDomain() }
    }

    override suspend fun getFavoriteRecipeByRecipeId(
        token: String,
        recipeId: String
    ): FavoriteRecipe {
        return culinaryChestAPI.getFavoriteRecipeByRecipeId(token, recipeId).toDomain()
    }

    override suspend fun createApplicationUserFavoriteRecipes(
        token: String, recipeId: Int, addedDate: CreateFavoriteRecipe
    ) {
        return culinaryChestAPI.createFavoriteRecipesUser(token, recipeId, addedDate.toDto())
    }


    override suspend fun deleteApplicationUserFavoriteRecipe(
        token: String, recipeId: String
    ) {
        return culinaryChestAPI.deleteFavoriteRecipeUser(token, recipeId)
    }
}