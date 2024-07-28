package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.UpdateStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class UpdateStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(updateStep: UpdateStep) {
        safeApiCall { repository.updateRecipeStep(updateStep) }
    }
}