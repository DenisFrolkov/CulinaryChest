package com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository

class UpdateStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        return repository.updateRecipeStep(token, recipeId, stepId, updateStep)
    }
}