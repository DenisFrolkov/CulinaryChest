package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class CreateStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(createStep: CreateStep) {
        safeApiCall { repository.createRecipeStep(createStep) }
    }
}