package com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class DeleteApplicationUserFavoriteRecipeUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String){
        safeApiCall {  repository.deleteApplicationUserFavoriteRecipe(token, recipeId) }
    }
}