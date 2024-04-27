package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeRepository {

    override suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return flow {
            val recipesFromCulinaryChestApi = try {
                culinaryChestAPI.getRecipes(token = token)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ProcessingResult.Error(message = "Error loading recipes"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ProcessingResult.Error(message = "Error loading http"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ProcessingResult.Error(message = "Error loading recipes"))
                return@flow
            }
            emit(ProcessingResult.Success(recipesFromCulinaryChestApi))
        }
    }
    override suspend fun getRecipeById(recipeId: String): ProcessingResult<Recipe> {
        return safeApiCall {
            culinaryChestAPI.getRecipeById(recipeId)
        }
    }

}