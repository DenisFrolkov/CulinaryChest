package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.culinarychest.domain.domain.ProcessingResult
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private val _recipe = MutableStateFlow<List<Recipe>>(emptyList())
    val recipe = _recipe.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    fun getRecipes(token: String) {
        viewModelScope.launch {
            recipeRepository.getRecipes(token).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is ProcessingResult.Success -> {
                        result.data?.let { recipes ->
                            _recipes.update { recipes }
                        }
                    }
                }
            }
        }
    }

    fun getRecipeById(token: String, recipeId: String){
        viewModelScope.launch {
            recipeRepository.getRecipeById(token, recipeId).collectLatest { result ->
                when(result) {
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
}

