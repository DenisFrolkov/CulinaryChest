package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipeRequest
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository

class UserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getUserFavoriteRecipes(token: Token): List<FavoriteRecipe> {
        return culinaryChestAPI.getListFavoriteRecipesUser(token.token).map { it.toDomain() }
    }

    override suspend fun getFavoriteRecipeByRecipeId(
        favoriteRecipeRequest: FavoriteRecipeRequest
    ): FavoriteRecipe {
        return culinaryChestAPI.getFavoriteRecipeByRecipeId(favoriteRecipeRequest.token, favoriteRecipeRequest.recipeId).toDomain()
    }

    override suspend fun createApplicationUserFavoriteRecipes(
        createFavoriteRecipe: CreateFavoriteRecipe
    ) {
        return culinaryChestAPI.createFavoriteRecipesUser(
            createFavoriteRecipe.token.token,
            createFavoriteRecipe.recipeId,
            createFavoriteRecipe.addedDate
        )
    }


    override suspend fun deleteApplicationUserFavoriteRecipe(
        favoriteRecipeRequest: FavoriteRecipeRequest
    ) {
        return culinaryChestAPI.deleteFavoriteRecipeUser(favoriteRecipeRequest.token, favoriteRecipeRequest.recipeId)
    }
}