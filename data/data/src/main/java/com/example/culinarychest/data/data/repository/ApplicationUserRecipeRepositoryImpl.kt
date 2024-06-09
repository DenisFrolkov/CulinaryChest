package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ApplicationUserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return flow {
            try {
                val recipes =
                    culinaryChestAPI.getApplicationUserRecipes(token).map { it.toDomain() }
                emit(ProcessingResult.Success(recipes))
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
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

        culinaryChestAPI.createApplicationUserRecipe(
            token,
            titlePart,
            recipeImagePart,
            ingredientsPart,
            stepsPart,
            creationDatePart,
            preparationTimePart
        )
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
        val recipeImagePart = if (recipeImage != null) {MultipartBody.Part.createFormData(
            "recipeImage",
            recipeImage.name,
            recipeImage.asRequestBody("image/*".toMediaTypeOrNull())
        ) } else null

        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredientsPart = ingredients.toRequestBody("text/plain".toMediaTypeOrNull())
        val preparationTimePart = preparationTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val creationDatePart = creationDate.toRequestBody("text/plain".toMediaTypeOrNull())


        culinaryChestAPI.updateApplicationUserRecipe(token, recipeId, titlePart, recipeImagePart, ingredientsPart, creationDatePart, preparationTimePart)
    }

    override suspend fun deleteApplicationUserRecipe(token: String, recipeId: String) {
        culinaryChestAPI.deleteApplicationUserRecipe(token, recipeId)
    }


}