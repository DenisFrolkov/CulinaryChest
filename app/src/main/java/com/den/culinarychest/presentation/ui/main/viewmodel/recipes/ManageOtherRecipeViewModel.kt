package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.AddedDateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipeRequest
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import kotlinx.coroutines.launch

class ManageOtherRecipeViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val createApplicationUserFavoriteRecipesUseCase: CreateApplicationUserFavoriteRecipesUseCase,
    private val deleteApplicationUserFavoriteRecipeUseCase: DeleteApplicationUserFavoriteRecipeUseCase
) : ViewModel() {

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun createFavoriteRecipesUser(
        recipeId: Int,
        addedDate: String
    ) {
        viewModelScope.launch {
            try {
                getToken?.let {
                    createApplicationUserFavoriteRecipesUseCase(
                        CreateFavoriteRecipe(
                            Token(it),
                            recipeId,
                            AddedDateFavoriteRecipe(addedDate)
                        )
                    )
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun deleteFavoriteRecipeUser(recipeId: String) {
        viewModelScope.launch {
            try {
                getToken?.let {
                    deleteApplicationUserFavoriteRecipeUseCase(
                        FavoriteRecipeRequest(
                            it,
                            recipeId
                        )
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

}