package com.example.culinarychest.domain.usecase.recipeRepositoryUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetRecipesUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(token: String, searchTerm: String?) : Flow<ProcessingResult<List<Recipe>>> {
        return repository.getRecipes(token, searchTerm)
    }
}