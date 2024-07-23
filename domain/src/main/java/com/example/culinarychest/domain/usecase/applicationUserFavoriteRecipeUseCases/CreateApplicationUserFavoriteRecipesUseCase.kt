package com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository

class CreateApplicationUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: String, recipeId: Int, addedDate: CreateFavoriteRecipe){
        return repository.createApplicationUserFavoriteRecipes(token, recipeId, addedDate)
    }
}