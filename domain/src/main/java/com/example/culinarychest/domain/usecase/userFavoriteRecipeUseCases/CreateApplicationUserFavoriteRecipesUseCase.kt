package com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class CreateApplicationUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe){
        safeApiCall { repository.createApplicationUserFavoriteRecipes(token, recipeId, addedDate) }
    }
}