package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _listRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipes = _listRecipes.asStateFlow()

    fun getListRecipes(token: String, searchTerm: String?) {
        viewModelScope.launch {
            getRecipesUseCase(token, searchTerm)
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