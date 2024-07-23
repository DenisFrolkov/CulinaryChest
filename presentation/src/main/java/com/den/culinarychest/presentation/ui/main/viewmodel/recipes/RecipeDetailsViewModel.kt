package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.usecase.applicationUserFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val getFavoriteRecipeByRecipeIdUseCase: GetFavoriteRecipeByRecipeIdUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : ViewModel() {

    private val _favoriteRecipeByRecipeId = MutableStateFlow<FavoriteRecipe?>(null)
    val favoriteRecipeByRecipeId = _favoriteRecipeByRecipeId.asStateFlow()

    private val _recipe = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = _recipe.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getRecipeById(token: String, recipeId: String) {
        viewModelScope.launch {
            getRecipeByIdUseCase(token, recipeId).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is ProcessingResult.Success -> {
                        result.data?.let { recipe ->
                            _recipe.update { recipe }
                        }
                    }
                }
            }
        }
    }

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