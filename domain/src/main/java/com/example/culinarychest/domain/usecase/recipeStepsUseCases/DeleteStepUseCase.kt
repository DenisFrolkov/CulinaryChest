package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.step.DeleteStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class DeleteStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(deleteStep: DeleteStep) {
        safeApiCall { repository.deleteRecipeStep(deleteStep) }
    }
}