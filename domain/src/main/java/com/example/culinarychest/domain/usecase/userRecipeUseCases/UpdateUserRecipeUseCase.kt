package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import java.io.File

class UpdateUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(
        token: String,
        recipeId: String,
        title: String,
        recipeImage: File? = null,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    ) {
        safeApiCall {
            repository.updateUserRecipe(
                token,
                recipeId,
                title,
                recipeImage,
                ingredients,
                creationDate,
                preparationTime
            )
        }
    }
}