package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getApplicationUserFavoriteRecipesUseCase: GetApplicationUserFavoriteRecipesUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase
) : ViewModel() {

    private val _userDtoFavoriteRecipes = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val userFavoriteRecipes = _userDtoFavoriteRecipes.asStateFlow()
    private val _recipesById = MutableStateFlow<List<Recipe>>(emptyList())
    val recipesById = _recipesById.asStateFlow()
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()
    fun getApplicationUserFavoriteRecipes(token: String) {
        viewModelScope.launch {
            getApplicationUserFavoriteRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipe ->
                                _userDtoFavoriteRecipes.update { favoriteRecipe }
                            }
                        }
                    }
                }
        }
    }

    fun getRecipesByIds(token: String, recipeIds: List<String>) {
        viewModelScope.launch {
            getRecipesByIdsUseCase(token, recipeIds).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is ProcessingResult.Success -> {
                        result.data?.let { recipes ->
                            _recipesById.update { recipes }
                        }
                    }

                }
            }
        }
    }

}