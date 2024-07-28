package com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class CreateApplicationUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(createFavoriteRecipe: CreateFavoriteRecipe){
        safeApiCall { repository.createApplicationUserFavoriteRecipes(createFavoriteRecipe) }
    }
}