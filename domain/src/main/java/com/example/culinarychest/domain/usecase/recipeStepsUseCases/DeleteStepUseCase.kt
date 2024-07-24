package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class DeleteStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, stepId: String) {
        safeApiCall { repository.deleteRecipeStep(token, recipeId, stepId) }
    }
}