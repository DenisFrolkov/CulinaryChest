package com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipeRequest
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall

class DeleteApplicationUserFavoriteRecipeUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(favoriteRecipeRequest: FavoriteRecipeRequest){
        safeApiCall {  repository.deleteApplicationUserFavoriteRecipe(favoriteRecipeRequest) }
    }
}