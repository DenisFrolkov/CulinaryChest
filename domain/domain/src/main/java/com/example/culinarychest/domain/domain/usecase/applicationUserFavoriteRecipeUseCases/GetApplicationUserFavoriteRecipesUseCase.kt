package com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.application_user.Token
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.repository.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.flow.Flow

class GetApplicationUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String): Flow<ProcessingResult<List<FavoriteRecipe>>>{
        return repository.getApplicationUserFavoriteRecipes(token)
    }
}