package com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteRecipeByRecipeIdUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String): Flow<ProcessingResult<FavoriteRecipe>>{
        return repository.getFavoriteRecipeByRecipeId(token, recipeId)
    }
}