package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getUserFavoriteRecipesUseCase: GetUserFavoriteRecipesUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase
) : ViewModel() {

    private val _listFavoriteRecipesUser = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val listFavoriteRecipesUser = _listFavoriteRecipesUser.asStateFlow()
    private val _listRecipesById = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipesById = _listRecipesById.asStateFlow()
    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getListFavoriteRecipesUser(token: String) {
        viewModelScope.launch {
            getUserFavoriteRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipe ->
                                _listFavoriteRecipesUser.update { favoriteRecipe }
                            }
                        }
                    }
                }
        }
    }

    fun getListRecipesByIds(token: String, recipeIds: List<String>) {
        viewModelScope.launch {
            getRecipesByIdsUseCase(token, recipeIds).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is ProcessingResult.Success -> {
                        result.data?.let { recipes ->
                            _listRecipesById.update { recipes }
                        }
                    }

                }
            }
        }
    }

}