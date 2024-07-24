package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import java.io.File

class CreateUserRecipeUseCase(
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
        safeApiCall {
            repository.createUserRecipe(
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
}
