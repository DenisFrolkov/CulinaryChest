package com.example.culinarychest.domain.usecase.recipeRepositoryUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeIdsRequest
import com.example.culinarychest.domain.repository.RecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRecipesByIdsUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(
        recipeIdsRequest: RecipeIdsRequest
    ): Flow<ProcessingResult<List<Recipe>>> = flow {
        emit(safeApiCall { repository.getRecipesByIds(recipeIdsRequest) })
    }
}