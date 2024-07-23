package com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>{
        return repository.getApplicationUserFavoriteRecipes(token)
    }
}