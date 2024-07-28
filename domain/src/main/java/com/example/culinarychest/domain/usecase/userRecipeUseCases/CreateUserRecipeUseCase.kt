package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class CreateUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(
        createRecipe: CreateRecipe
    ) {
        safeApiCall {
            repository.createUserRecipe(
                createRecipe
            )
        }
    }
}
