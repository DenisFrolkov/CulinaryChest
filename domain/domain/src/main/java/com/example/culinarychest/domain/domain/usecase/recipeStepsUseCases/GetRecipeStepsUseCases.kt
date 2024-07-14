package com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.repository.RecipeRepository
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow

class GetRecipeStepsUseCases(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String): Flow<ProcessingResult<List<Step>>> {
        return repository.getRecipeSteps(token, recipeId)
    }
}