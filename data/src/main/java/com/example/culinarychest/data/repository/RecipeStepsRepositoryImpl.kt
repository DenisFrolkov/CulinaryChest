package com.example.culinarychest.data.repository

import com.example.culinarychest.data.api.CulinaryChestAPI
import com.example.culinarychest.data.model.Mappers.toDomain
import com.example.culinarychest.data.model.Mappers.toDto
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.Step
import com.example.culinarychest.domain.model.step.DeleteStep
import com.example.culinarychest.domain.model.step.UpdateStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(
        recipeRequest: RecipeRequest
    ): List<Step> {
        return culinaryChestAPI.getListStepsRecipe(recipeRequest.token.token, recipeRequest.recipeId).map { it.toDomain() }
    }

    override suspend fun createRecipeStep(createStep: CreateStep) {
        return culinaryChestAPI.createStepRecipe(createStep.token.token, createStep.recipeId, createStep.stepData.toDto())
    }

    override suspend fun updateRecipeStep(
        updateStep: UpdateStep
    ) {
        return culinaryChestAPI.updateStepRecipe(updateStep.token.token, updateStep.recipeId, updateStep.stepId, updateStep.stepData.toDto())
    }

    override suspend fun deleteRecipeStep(deleteStep: DeleteStep) {
        return culinaryChestAPI.deleteStepRecipe(deleteStep.token.token, deleteStep.recipeId, deleteStep.stepId)
    }
}