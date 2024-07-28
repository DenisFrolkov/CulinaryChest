package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getUserRecipes(token: Token): List<Recipe> {
        return culinaryChestAPI.getListRecipesUser(token.token).map { it.toDomain() }
    }

    override suspend fun createUserRecipe(
        createRecipe: CreateRecipe
    ) {
        val recipeImagePart = MultipartBody.Part.createFormData(
            "recipeImage",
            createRecipe.recipeImage.name,
            createRecipe.recipeImage.asRequestBody("image/*".toMediaTypeOrNull())
        )
        val titlePart = createRecipe.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredientsPart = createRecipe.ingredients.toRequestBody("text/plain".toMediaTypeOrNull())
        val stepsPart = createRecipe.steps.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val creationDatePart = createRecipe.creationDate.toRequestBody("text/plain".toMediaTypeOrNull())
        val preparationTimePart = createRecipe.preparationTime.toRequestBody("text/plain".toMediaTypeOrNull())

        return culinaryChestAPI.createRecipeUser(
            createRecipe.token.token,
            titlePart,
            recipeImagePart,
            ingredientsPart,
            stepsPart,
            creationDatePart,
            preparationTimePart
        )
    }

    override suspend fun updateUserRecipe(
        updateRecipe: UpdateRecipe
    ) {
        val recipeImagePart = if (updateRecipe.recipeImage != null) {
            MultipartBody.Part.createFormData(
                "recipeImage",
                updateRecipe.recipeImage?.name,
                updateRecipe.recipeImage!!.asRequestBody("image/*".toMediaTypeOrNull())
            )
        } else null
        val titlePart = updateRecipe.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val ingredientsPart = updateRecipe.ingredients.toRequestBody("text/plain".toMediaTypeOrNull())
        val preparationTimePart = updateRecipe.preparationTime.toRequestBody("text/plain".toMediaTypeOrNull())
        val creationDatePart = updateRecipe.creationDate.toRequestBody("text/plain".toMediaTypeOrNull())

        return culinaryChestAPI.updateRecipeUser(
            updateRecipe.token.token,
            updateRecipe.recipeId,
            titlePart,
            recipeImagePart,
            ingredientsPart,
            creationDatePart,
            preparationTimePart
        )
    }

    override suspend fun deleteUserRecipe(recipeRequest: RecipeRequest) {
        return culinaryChestAPI.deleteRecipeUser(
            recipeRequest.token.token,
            recipeRequest.recipeId
        )
    }
}