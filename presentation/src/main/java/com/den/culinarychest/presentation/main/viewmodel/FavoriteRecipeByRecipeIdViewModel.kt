package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteRecipeByRecipeIdViewModel(
    private val getFavoriteRecipeByRecipeIdUseCase: GetFavoriteRecipeByRecipeIdUseCase,
) : ViewModel() {


    private val _favoriteRecipeByRecipeId = MutableStateFlow<FavoriteRecipe?>(null)
    val favoriteRecipeByRecipeId = _favoriteRecipeByRecipeId.asStateFlow()

    fun getFavoriteRecipeByRecipeId(token: String, recipeId: String) {
        viewModelScope.launch {
            getFavoriteRecipeByRecipeIdUseCase(token, recipeId)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _favoriteRecipeByRecipeId.update { null }
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipeByRecipeId ->
                                _favoriteRecipeByRecipeId.update { favoriteRecipeByRecipeId }
                            }
                        }
                    }
                }
        }
    }

}