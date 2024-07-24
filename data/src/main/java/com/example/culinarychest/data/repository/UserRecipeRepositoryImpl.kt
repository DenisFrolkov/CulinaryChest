package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getUserRecipes(token: String): List<Recipe> {
        return culinaryChestAPI.getListRecipesUser(token).map { it.toDomain() }
    }

    override suspend fun createUserRecipe(
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

        return culinaryChestAPI.createRecipeUser(
            token,
            titlePart,
            recipeImagePart,
            ingredientsPart,
            stepsPart,
            creationDatePart,
            preparationTimePart
        )
    }

    override suspend fun updateUserRecipe(
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

        return culinaryChestAPI.updateRecipeUser(
            token,
            recipeId,
            titlePart,
            recipeImagePart,
            ingredientsPart,
            creationDatePart,
            preparationTimePart
        )
    }

    override suspend fun deleteUserRecipe(token: String, recipeId: String) {
        return culinaryChestAPI.deleteRecipeUser(
            token,
            recipeId
        )
    }
}