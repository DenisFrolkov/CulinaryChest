package com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.repository.ApplicationUserFavoriteRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserFavoriteRecipesUseCase(
    private val repository: ApplicationUserFavoriteRecipeRepository
) {
    suspend operator fun invoke(token: Token): Flow<ProcessingResult<List<FavoriteRecipe>>> =
        flow {
            emit(safeApiCall { repository.getUserFavoriteRecipes(token) })
        }
}
