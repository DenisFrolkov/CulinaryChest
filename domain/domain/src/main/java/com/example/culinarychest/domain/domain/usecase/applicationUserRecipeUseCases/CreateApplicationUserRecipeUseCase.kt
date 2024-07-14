package com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases

import com.example.culinarychest.domain.domain.repository.ApplicationUserRecipeRepository
import java.io.File

class CreateApplicationUserRecipeUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(
        token: String,
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: String,
        creationDate: String,
        preparationTime: String
    ) {
        return repository.createApplicationUserRecipe(
            token,
            title,
            recipeImage,
            ingredients,
            steps,
            creationDate,
            preparationTime
        )
    }
}
