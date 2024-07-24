package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.data.model.Mappers.toDto
import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.Step
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(
        token: String,
        recipeId: String
    ): List<Step> {
        return culinaryChestAPI.getListStepsRecipe(token, recipeId).map { it.toDomain() }
    }

    override suspend fun createRecipeStep(token: String, recipeId: String, step: CreateStep) {
        return culinaryChestAPI.createStepRecipe(token, recipeId, step.toDto())
    }

    override suspend fun updateRecipeStep(
        token: String,
        recipeId: String,
        stepId: String,
        updateStep: CreateStep
    ) {
        return culinaryChestAPI.updateStepRecipe(token, recipeId, stepId, updateStep.toDto())
    }

    override suspend fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        return culinaryChestAPI.deleteStepRecipe(token, recipeId, stepId)
    }
}