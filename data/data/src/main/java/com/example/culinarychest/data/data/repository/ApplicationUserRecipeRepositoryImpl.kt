package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import kotlinx.coroutines.flow.Flow

class ApplicationUserRecipeRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : ApplicationUserRecipeRepository {

    override suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return safeApiCall {
            culinaryChestAPI.getApplicationUserRecipes(token)
        }
    }
    override suspend fun createApplicationUserRecipe(token: String, title: String, recipeImage: String, ingredients: String, step: List<CreateStep>, creationDate: String, preparationTime: String) {
        culinaryChestAPI.createApplicationUserRecipe(token, title, recipeImage, ingredients, step, creationDate, preparationTime)
    }
    override suspend fun updateApplicationUserRecipe(token: String, recipeId: String, recipe: UpdateRecipe) {
        culinaryChestAPI.updateApplicationUserRecipe(token, recipeId, recipe)
    }
    override suspend fun deleteApplicationUserRecipe(token: String, recipeId: String) {
        culinaryChestAPI.deleteApplicationUserRecipe(token, recipeId)
    }

}