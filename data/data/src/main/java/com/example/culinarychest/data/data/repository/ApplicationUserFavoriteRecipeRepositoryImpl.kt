package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApplicationUserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>> {
        return flow {
            try {
                val recipes = culinaryChestAPI.getApplicationUserFavoriteRecipes(token).map { it.toDomain() }
                emit(ProcessingResult.Success(recipes))
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun getFavoriteRecipeByRecipeId(
        token: String,
        recipeId: String
    ): Flow<ProcessingResult<FavoriteRecipe>> {
        return flow {
            try {
                val recipe = culinaryChestAPI.getFavoriteRecipeByRecipeId(token, recipeId).toDomain()
                emit(ProcessingResult.Success(recipe))
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }
    }

    override suspend fun createApplicationUserFavoriteRecipes(
        token: String, recipeId: Int, addedDate: CreateFavoriteRecipe
    ) {
        culinaryChestAPI.createApplicationUserFavoriteRecipes(token, recipeId, addedDate.toDto())
    }

    override suspend fun deleteApplicationUserFavoriteRecipe(
        token: String, favoriteRecipeId: String
    ) {
        culinaryChestAPI.deleteApplicationUserFavoriteRecipe(token, favoriteRecipeId)
    }

}