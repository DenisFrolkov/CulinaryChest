package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.SearchRequest
import com.example.culinarychest.domain.usecase.GetRecipePhotoUseCase
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getRecipePhotoUseCase: GetRecipePhotoUseCase,
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _listRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipes = _listRecipes.asStateFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
            getToken?.let { token ->
                getListRecipes(null)
            }
        }
    }

    fun getListRecipes(searchTerm: String?) {
        viewModelScope.launch {
            getToken?.let { token ->
                getRecipesUseCase(SearchRequest(Token(token), searchTerm))
                    .collectLatest { result ->
                        when (result) {
                            is ProcessingResult.Error -> {
                            }
                            is ProcessingResult.Success -> {
                                result.data?.let { userInfo ->
                                    _listRecipes.value = userInfo
                                    loadImagesForRecipes(userInfo)
                                }
                            }
                            is ProcessingResult.Loading -> {
                            }
                        }
                    }
            }
        }
    }

    private fun loadImagesForRecipes(recipes: List<Recipe>) {
        recipes.forEach { recipe ->
            viewModelScope.launch {
                try {
                    val url = getRecipePhotoUseCase(recipe.imageUrl)
                    _listRecipes.update { currentRecipes ->
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