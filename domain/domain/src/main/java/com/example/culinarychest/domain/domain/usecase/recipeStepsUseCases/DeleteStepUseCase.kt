package com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository

class DeleteStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, stepId: String) {
        return repository.deleteRecipeStep(token, recipeId, stepId)
    }
}