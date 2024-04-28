package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.culinarychest.domain.domain.ProcessingResult
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.dataclasses.Token
import com.example.culinarychest.domain.domain.interfaces.RecipeRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val applicationUserViewModel: ApplicationUserViewModel
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        applicationUserViewModel.token.observeForever() { token ->
            if (token != null) {
                getRecipes(token)
            }
        }
    }

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
}

