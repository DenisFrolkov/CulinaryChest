package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserRecipeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationUserRecipeViewModel(
    private val applicationUserRecipeRepository: ApplicationUserRecipeRepository
) : ViewModel() {

    private val _applicationUserRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val applicationUserRecipes = _applicationUserRecipes.asStateFlow()

    private val _createdRecipeResult = MutableLiveData<ProcessingResult<CreateRecipe>>()
    val createdRecipeResult: LiveData<ProcessingResult<CreateRecipe>> = _createdRecipeResult

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getApplicationUserRecipes(token: String) {
        viewModelScope.launch {
            applicationUserRecipeRepository.getApplicationUserRecipes(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { applicationUserRecipes ->
                                _applicationUserRecipes.update { applicationUserRecipes }
                            }
                        }
                    }
                }
        }
    }

    fun createApplicationUserRecipe(token: String, recipe: CreateRecipe) {
        viewModelScope.launch {
            try {
                applicationUserRecipeRepository.createApplicationUserRecipe(
                    token, recipe
                )
                _createdRecipeResult.value = ProcessingResult.Success(null)
            } catch (e: Exception) {
                _createdRecipeResult.value =
                    ProcessingResult.Error(message = "Error create recipe: ${e.message}")
                _showErrorToastChannel.send(true)
            }
        }
    }

    fun updateApplicationUserRecipe(token: String, recipeId: String, recipe: UpdateRecipe) {
        viewModelScope.launch {
            try {
                applicationUserRecipeRepository.updateApplicationUserRecipe(
                    token, recipeId, recipe
                )
            } catch (e: Exception) {

            }
        }
    }

    fun deleteApplicationUserRecipe(token: String, recipeId: String) {
        viewModelScope.launch {
            try {
                applicationUserRecipeRepository.deleteApplicationUserRecipe(
                    token,
                    recipeId
                )
            } catch (e: Exception) {

            }
        }
    }
}