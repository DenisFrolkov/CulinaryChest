package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _listRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipes = _listRecipes.asStateFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun getListRecipes(searchTerm: String?) {
        viewModelScope.launch {
            getToken?.let {
                getRecipesUseCase(it, searchTerm)
                    .collectLatest { result ->
                        when (result) {
                            is ProcessingResult.Error -> {
                                // Handle error
                            }

                            is ProcessingResult.Success -> {
                                result.data?.let { userInfo ->
                                    _listRecipes.update { userInfo }
                                }
                            }
                        }
                    }
            }
        }
    }

}