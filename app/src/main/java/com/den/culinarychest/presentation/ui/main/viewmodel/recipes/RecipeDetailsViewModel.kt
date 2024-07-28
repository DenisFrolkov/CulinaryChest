package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipeRequest
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getFavoriteRecipeByRecipeIdUseCase: GetFavoriteRecipeByRecipeIdUseCase,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : ViewModel() {

    private val _favoriteRecipeByRecipeId = MutableStateFlow<FavoriteRecipe?>(null)
    val favoriteRecipeByRecipeId = _favoriteRecipeByRecipeId.asStateFlow()

    private val _recipe = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = _recipe.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            getToken?.let {
                getRecipeByIdUseCase(RecipeRequest(Token(it), recipeId)).collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { recipe ->
                                _recipe.update { recipe }
                            }
                        }
                        is ProcessingResult.Loading -> {

                        }
                    }
                }
            }
        }
    }

    fun getFavoriteRecipeByRecipeId(recipeId: String) {
        viewModelScope.launch {
            getToken?.let {
                getFavoriteRecipeByRecipeIdUseCase(FavoriteRecipeRequest(it, recipeId))
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
                            is ProcessingResult.Loading -> {

                            }
                        }
                    }
            }
        }
    }
}