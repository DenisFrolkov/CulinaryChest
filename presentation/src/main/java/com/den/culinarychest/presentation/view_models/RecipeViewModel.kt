package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.culinarychest.domain.domain.ProcessingResult
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                val result = recipeRepository.getRecipes()
                if (result is ProcessingResult.Success) {
                    _recipes.value = result.data ?: emptyList()
                } else {
                    _showErrorToastChannel.send(true)
                }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            try {
                val result = recipeRepository.getRecipeById(recipeId)
                if (result is ProcessingResult.Success) {
                    result
                } else {
                    _showErrorToastChannel.send(true)
                }
            } catch (e: Exception) {
                _showErrorToastChannel.send(true)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
