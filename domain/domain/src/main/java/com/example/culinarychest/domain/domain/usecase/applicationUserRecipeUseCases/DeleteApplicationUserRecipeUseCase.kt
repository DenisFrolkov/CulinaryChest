package com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases

import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository

class DeleteApplicationUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String){
        return repository.deleteApplicationUserRecipe(token, recipeId)
    }
}