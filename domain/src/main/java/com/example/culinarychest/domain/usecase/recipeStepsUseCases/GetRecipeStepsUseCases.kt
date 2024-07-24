package com.example.culinarychest.domain.usecase.recipeStepsUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.step.Step
import com.example.culinarychest.domain.repository.RecipeRepository
import com.example.culinarychest.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipeStepsUseCases(
    private val repository: RecipeStepsRepository
) {
    suspend operator fun invoke(token: String, recipeId: String): Flow<ProcessingResult<List<Step>>> = flow {
        emit(safeApiCall { repository.getRecipeSteps(token, recipeId) })
    }
}