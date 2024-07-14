package com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationUserRecipesUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<List<Recipe>>> {
        return repository.getApplicationUserRecipes(token)
    }
}