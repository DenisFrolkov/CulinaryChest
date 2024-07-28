package com.example.culinarychest.domain.usecase.userRecipeUseCases

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.repository.ApplicationUserRecipeRepository
import com.example.culinarychest.domain.usecase.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserRecipesUseCase(
    private val repository: ApplicationUserRecipeRepository
) {
    suspend operator fun invoke(token: Token): Flow<ProcessingResult<List<Recipe>>> = flow {
        emit(safeApiCall { repository.getUserRecipes(token) })
    }
}