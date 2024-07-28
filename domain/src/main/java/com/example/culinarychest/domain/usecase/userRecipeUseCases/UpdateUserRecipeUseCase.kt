package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class UpdateUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(
        updateRecipe: UpdateRecipe
    ) {
        safeApiCall {
            repository.updateUserRecipe(
                updateRecipe = updateRecipe
            )
        }
    }
}