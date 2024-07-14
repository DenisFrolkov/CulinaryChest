package com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetRecipesByIdsUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(token: String, recipeIds: List<String>): Flow<ProcessingResult<List<Recipe>>> {
        return repository.getRecipesByIds(token, recipeIds)
    }
}