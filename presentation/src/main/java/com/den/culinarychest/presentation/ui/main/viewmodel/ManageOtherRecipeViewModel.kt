package com.den.culinarychest.presentation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import kotlinx.coroutines.launch

class ManageOtherRecipeViewModel(
    private val createApplicationUserFavoriteRecipesUseCase: CreateApplicationUserFavoriteRecipesUseCase,
    private val deleteApplicationUserFavoriteRecipeUseCase: DeleteApplicationUserFavoriteRecipeUseCase
    ) : ViewModel() {

    fun createFavoriteRecipesUser(
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

    fun deleteFavoriteRecipeUser(token: String, recipeId: String) {
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