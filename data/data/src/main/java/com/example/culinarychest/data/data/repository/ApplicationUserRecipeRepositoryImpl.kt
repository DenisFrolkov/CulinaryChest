package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.domain.model.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class ApplicationUserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> =
        flow {
            try {
                val response =
                    culinaryChestAPI.getApplicationUserRecipes(token).map { it.toDomain() }
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun createApplicationUserRecipe(
        token: String,
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: String,
        creationDate: String,
        preparationTime: String
    ) {
        val recipeImagePart = MultipartBody.Part.createFormData(
            "recipeImage",
            recipeImage.name,
            recipeImage.asRequestBody("image/*".toMediaTypeOrNull())
        )
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredientsPart = ingredients.toRequestBody("text/plain".toMediaTypeOrNull())
        val stepsPart = steps.toRequestBody("text/plain".toMediaTypeOrNull())
        val creationDatePart = creationDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val preparationTimePart = preparationTime.toRequestBody("text/plain".toMediaTypeOrNull())

        try {
            culinaryChestAPI.createApplicationUserRecipe(
                token,
                titlePart,
                recipeImagePart,
                ingredientsPart,
                stepsPart,
                creationDatePart,
                preparationTimePart
            )
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }

    override suspend fun updateApplicationUserRecipe(
        token: String,
        recipeId: String,
        title: String,
        recipeImage: File?,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    ) {
        val recipeImagePart = if (recipeImage != null) {
            MultipartBody.Part.createFormData(
                "recipeImage",
                recipeImage.name,
                recipeImage.asRequestBody("image/*".toMediaTypeOrNull())
            )
        } else null
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredientsPart = ingredients.toRequestBody("text/plain".toMediaTypeOrNull())
        val preparationTimePart = preparationTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val creationDatePart = creationDate.toRequestBody("text/plain".toMediaTypeOrNull())

        try {
            culinaryChestAPI.updateApplicationUserRecipe(
                token,
                recipeId,
                titlePart,
                recipeImagePart,
                ingredientsPart,
                creationDatePart,
                preparationTimePart
            )
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }

    override suspend fun deleteApplicationUserRecipe(token: String, recipeId: String) {
        try {
            culinaryChestAPI.deleteApplicationUserRecipe(
                token,
                recipeId
            )
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }

    }
}