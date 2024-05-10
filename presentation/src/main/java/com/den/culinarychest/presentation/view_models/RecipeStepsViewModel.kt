package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.repository.ProcessingResult
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeStepsViewModel(
    private val recipeStepsRepository: RecipeStepsRepository
) : ViewModel() {

    private val _recipeSteps = MutableStateFlow<List<Step>>(emptyList())
    val recipeSteps = _recipeSteps.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getRecipeSteps(token: String, recipeId: String) {
        viewModelScope.launch {
            recipeStepsRepository.getRecipeSteps(token, recipeId).collectLatest { result ->
                when (result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is ProcessingResult.Success -> {
                        result.data?.let { applicationUserRecipes ->
                            _recipeSteps.update { applicationUserRecipes }
                        }
                    }
                }
            }
        }
    }

    fun createRecipeSteps(token: String, recipeId: String, step: CreateStep) {
        viewModelScope.launch {
            try {
                recipeStepsRepository.createRecipeStep(token, recipeId, step)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        viewModelScope.launch {
            try {
                recipeStepsRepository.updateRecipeStep(token, recipeId, stepId, updateStep)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
    fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        viewModelScope.launch {
            try {
                recipeStepsRepository.deleteRecipeStep(token, recipeId, stepId)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
}