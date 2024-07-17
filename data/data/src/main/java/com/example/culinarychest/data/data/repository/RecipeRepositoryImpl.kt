package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.repository.RecipeRepository
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
    ): Flow<ProcessingResult<List<Recipe>>> =
        flow {
            try {
                val response = culinaryChestAPI.getListRecipes(token, searchTerm).map { it.toDomain() }
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun getRecipesByIds(
        token: String,
        recipeIds: List<String>
    ): Flow<ProcessingResult<List<Recipe>>> = flow {
        try {
            val response = culinaryChestAPI.getListRecipeByIds(token, recipeIds).map { it.toDomain() }
            emit(ProcessingResult.Success(response))
        } catch (e: HttpException) {
            emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    override suspend fun getRecipeById(
        token: String,
        recipeId: String
    ): Flow<ProcessingResult<List<Recipe>>> =
        flow {
            try {
                val response = culinaryChestAPI.getRecipeById(token, recipeId).map { it.toDomain() }
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }
}
