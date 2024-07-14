package com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteRecipeByRecipeIdUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String): Flow<ProcessingResult<FavoriteRecipe>>{
        return repository.getFavoriteRecipeByRecipeId(token, recipeId)
    }
}