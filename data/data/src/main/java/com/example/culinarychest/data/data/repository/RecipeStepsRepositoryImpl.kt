package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(token: String, recipeId: String): Flow<ProcessingResult<List<Step>>> {
        return flow {
            try {
                val recipes = culinaryChestAPI.getRecipeSteps(token, recipeId).map { it.toDomain() }
                emit(ProcessingResult.Success(recipes))
            } catch (e: Exception) {
                emit(ProcessingResult.Error(e.message ?: "An error occurred"))
            }
        }
    }
    override suspend fun createRecipeStep(token: String, recipeId: String, step: CreateStep) {
        culinaryChestAPI.createRecipeStep(token, recipeId, step.toDto())
    }
    override suspend fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        culinaryChestAPI.updateRecipeStep(token, recipeId, stepId, updateStep.toDto())
    }
    override suspend fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        culinaryChestAPI.deleteRecipeStep(token, recipeId, stepId)
    }

}