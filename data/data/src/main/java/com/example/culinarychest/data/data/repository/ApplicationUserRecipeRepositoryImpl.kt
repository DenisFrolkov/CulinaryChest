package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        step: List<CreateStep>,
        creationDate: String,
        preparationTime: String
    ) {
        val titleRequestBody = title.toPlainRequestBody()
        val ingredientsRequestBody = ingredients.toPlainRequestBody()
        val stepsRequestBody = Gson().toJson(step).toPlainRequestBody()
        val creationDateRequestBody = creationDate.toPlainRequestBody()
        val preparationTimeRequestBody = preparationTime.toPlainRequestBody()

        val recipeImagePart = prepareFilePart("recipeImage", recipeImage)

        culinaryChestAPI.createApplicationUserRecipe(
            token,
            titleRequestBody,
            recipeImagePart,
            ingredientsRequestBody,
            stepsRequestBody,
            creationDateRequestBody,
            preparationTimeRequestBody
        )
    }


    override suspend fun updateApplicationUserRecipe(
        token: String,
        recipeId: String,
        recipe: UpdateRecipe
    ) {
        culinaryChestAPI.updateApplicationUserRecipe(token, recipeId, recipe.toDto())
    }

    override suspend fun deleteApplicationUserRecipe(token: String, recipeId: String) {
        culinaryChestAPI.deleteApplicationUserRecipe(token, recipeId)
    }


}

fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
    val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

fun String.toPlainRequestBody(): RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}