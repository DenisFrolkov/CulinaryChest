package com.example.culinarychest.domain.usecase.applicationUserRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import java.io.File

class UpdateApplicationUserRecipeUseCase(
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
        return repository.updateApplicationUserRecipe(
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