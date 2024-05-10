package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(token: String, recipeId: String): Flow<ProcessingResult<List<Step>>> {
        return safeApiCall {
            culinaryChestAPI.getRecipeSteps(token, recipeId)
        }
    }
    override suspend fun createRecipeStep(token: String, recipeId: String, step: CreateStep) {
        culinaryChestAPI.createRecipeStep(token, recipeId, step)
    }
    override suspend fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        culinaryChestAPI.updateRecipeStep(token, recipeId, stepId, updateStep)
    }
    override suspend fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        culinaryChestAPI.deleteRecipeStep(token, recipeId, stepId)
    }

}