package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(
        token: String,
        searchTerm: String?
    ): List<Recipe> {
        return culinaryChestAPI.getListRecipes(token, searchTerm).map { it.toDomain() }
    }

    override suspend fun getRecipesByIds(
        token: String,
        recipeIds: List<String>
    ): List<Recipe> {
        return culinaryChestAPI.getListRecipeByIds(token, recipeIds).map { it.toDomain() }
    }

    override suspend fun getRecipeById(
        token: String,
        recipeId: String
    ): List<Recipe> {
        return culinaryChestAPI.getRecipeById(token, recipeId).map { it.toDomain() }
    }
}
