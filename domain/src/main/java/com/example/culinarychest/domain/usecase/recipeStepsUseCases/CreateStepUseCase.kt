package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.repository.RecipeStepsRepository

class CreateStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, step: CreateStep) {
        return repository.createRecipeStep(token, recipeId, step)
    }
}