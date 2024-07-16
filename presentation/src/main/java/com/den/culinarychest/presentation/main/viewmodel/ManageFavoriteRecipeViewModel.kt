package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import kotlinx.coroutines.launch

class ManageFavoriteRecipeViewModel(
    private val createApplicationUserFavoriteRecipesUseCase: CreateApplicationUserFavoriteRecipesUseCase,
    private val deleteApplicationUserFavoriteRecipeUseCase: DeleteApplicationUserFavoriteRecipeUseCase
    ) : ViewModel() {
    fun createApplicationUserFavoriteRecipes(
        token: String,
        recipeId: Int,
        addedDate: CreateFavoriteRecipe
    ) {
        viewModelScope.launch {
            try {
                createApplicationUserFavoriteRecipesUseCase(
                    token,
                    recipeId,
                    addedDate
                )
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun deleteApplicationUserFavoriteRecipe(token: String, recipeId: String) {
        viewModelScope.launch {
            try {
                deleteApplicationUserFavoriteRecipeUseCase(
                    token,
                    recipeId
                )
            } catch (e: Exception) {

            }
        }
    }

}