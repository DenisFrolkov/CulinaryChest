package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.domain.model.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ApplicationUserFavoriteRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserFavoriteRecipeRepository {

    override suspend fun getApplicationUserFavoriteRecipes(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>> =
        flow {
            try {
                val response = culinaryChestAPI.getApplicationUserFavoriteRecipes(token).map { it.toDomain() }
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun getFavoriteRecipeByRecipeId( token: String, recipeId: String ): Flow<ProcessingResult<FavoriteRecipe>> =
        flow {
            try {
                val response =
                    culinaryChestAPI.getFavoriteRecipeByRecipeId(token, recipeId).toDomain()
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun createApplicationUserFavoriteRecipes(
        token: String, recipeId: Int, addedDate: CreateFavoriteRecipe
    ) {
        try {
            culinaryChestAPI.createApplicationUserFavoriteRecipes(token, recipeId, addedDate.toDto())
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }


    override suspend fun deleteApplicationUserFavoriteRecipe(
        token: String, recipeId: String
    ) {
        try {
            culinaryChestAPI.deleteApplicationUserFavoriteRecipe(token, recipeId)
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }

}