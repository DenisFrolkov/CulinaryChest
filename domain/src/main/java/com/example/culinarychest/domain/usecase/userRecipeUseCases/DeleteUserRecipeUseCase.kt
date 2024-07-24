package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class DeleteUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: String){
        safeApiCall {  repository.deleteUserRecipe(token, recipeId) }
    }
}