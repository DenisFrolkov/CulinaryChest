package com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository

class DeleteApplicationUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String){
        return repository.deleteApplicationUserRecipe(token, recipeId)
    }
}