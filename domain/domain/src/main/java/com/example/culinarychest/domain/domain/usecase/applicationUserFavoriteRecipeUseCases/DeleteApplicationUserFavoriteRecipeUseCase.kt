package com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.domain.repository.ApplicationUserFavoriteRecipeRepository

class DeleteApplicationUserFavoriteRecipeUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String){
        return repository.deleteApplicationUserFavoriteRecipe(token, recipeId)
    }
}