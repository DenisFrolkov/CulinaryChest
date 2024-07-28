package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeIdsRequest
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.recipe.SearchRequest
import com.example.culinarychest.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(
        searchRequest: SearchRequest
    ): List<Recipe> {
        return culinaryChestAPI.getListRecipes(searchRequest.token.token, searchRequest.searchTerm).map { it.toDomain() }
    }

    override suspend fun getRecipesByIds(
        recipeIdsRequest: RecipeIdsRequest
    ): List<Recipe> {
        return culinaryChestAPI.getListRecipeByIds(recipeIdsRequest.token.token, recipeIdsRequest.recipeIds).map { it.toDomain() }
    }

    override suspend fun getRecipeById(
        recipeRequest: RecipeRequest
    ): List<Recipe> {
        return culinaryChestAPI.getRecipeById(recipeRequest.token.token, recipeRequest.recipeId).map { it.toDomain() }
    }
}
