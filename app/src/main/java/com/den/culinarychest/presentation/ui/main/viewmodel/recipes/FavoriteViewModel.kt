package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeIdsRequest
import com.example.culinarychest.domain.usecase.GetRecipePhotoUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userFavoriteRecipeUseCases.GetUserFavoriteRecipesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getRecipePhotoUseCase: GetRecipePhotoUseCase,
    private val getUserFavoriteRecipesUseCase: GetUserFavoriteRecipesUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase
) : ViewModel() {

    private val _listFavoriteRecipesUser = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val listFavoriteRecipesUser = _listFavoriteRecipesUser.asStateFlow()

    private val _listRecipesById = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipesById = _listRecipesById.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun getListFavoriteRecipesUser() {
        viewModelScope.launch {
            getToken?.let {
                getUserFavoriteRecipesUseCase(Token(it))
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
                            is ProcessingResult.Loading -> {

                            }
                        }
                    }
            }
        }
    }

    fun getListRecipesByIds(recipeIds: List<String>) {
        viewModelScope.launch {
            getToken?.let {
                getRecipesByIdsUseCase(RecipeIdsRequest(Token(it), recipeIds)).collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { recipes ->
                                _listRecipesById.update { recipes }
                                loadImagesForFavoriteRecipes(recipes)
                            }
                        }

                        is ProcessingResult.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private fun loadImagesForFavoriteRecipes(favoriteRecipes: List<Recipe>) {
        favoriteRecipes.forEach { recipe ->
            viewModelScope.launch {
                try {
                    val url = getRecipePhotoUseCase(recipe.imageUrl)
                    _listRecipesById.update { currentRecipes ->
                        currentRecipes.map {
                            if (it.imageUrl == recipe.imageUrl) {
                                it.copy(imageUrl = url)
                            } else {
                                it
                            }
                        }
                    }
                } catch (e: Exception) {

                }
            }
        }
    }

}