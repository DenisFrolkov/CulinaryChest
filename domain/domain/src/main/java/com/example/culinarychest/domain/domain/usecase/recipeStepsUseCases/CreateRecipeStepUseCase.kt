package com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow

class CreateRecipeStepUseCase(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String, step: CreateStep) {
        return repository.createRecipeStep(token, recipeId, step)
    }
}