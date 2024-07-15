package com.den.culinarychest.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val getRecipesByIdsUseCase: GetRecipesByIdsUseCase,
    private val getRecipesUseCase: GetRecipesUseCase
) : ViewModel() {

    private val _listRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipes = _listRecipes.asStateFlow()

    private val _recipesById = MutableStateFlow<List<Recipe>>(emptyList())
    val recipesById = _recipesById.asStateFlow()

    private val _recipe = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = _recipe.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    fun getRecipes(token: String, searchTerm: String?) {
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

    override fun onCleared() {
        Log.d("AAA", "onCleared")
        super.onCleared()
        viewModelScope.cancel()
    }

    fun clear(){
        onCleared()
    }
}

