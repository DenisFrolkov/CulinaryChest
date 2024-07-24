package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class UpdateStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        safeApiCall { repository.updateRecipeStep(token, recipeId, stepId, updateStep) }
    }
}