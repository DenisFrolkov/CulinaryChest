package com.den.culinarychest.presentation.main.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipeByIdUseCase
import com.example.culinarychest.domain.domain.usecase.recipeRepositoryUseCases.GetRecipesByIdsUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.CreateRecipeStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.DeleteRecipeStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.GetRecipeStepsUseCases
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.UpdateRecipeStepUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeStepsViewModel(
    private val createRecipeStepUseCase: CreateRecipeStepUseCase,
    private val deleteRecipeStepUseCase: DeleteRecipeStepUseCase,
    private val getRecipeStepsUseCases: GetRecipeStepsUseCases,
    private val updateRecipeStepUseCase: UpdateRecipeStepUseCase
) : ViewModel() {

    private val _recipeSteps = MutableStateFlow<List<Step>>(emptyList())
    val recipeSteps = _recipeSteps.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getRecipeSteps(token: String, recipeId: String) {
        viewModelScope.launch {
            getRecipeStepsUseCases(token, recipeId).collectLatest { result ->
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
                createRecipeStepUseCase(token, recipeId, step)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        viewModelScope.launch {
            try {
                updateRecipeStepUseCase(token, recipeId, stepId, updateStep)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
    fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        viewModelScope.launch {
            try {
                deleteRecipeStepUseCase(token, recipeId, stepId)
            } catch (e: Exception) {
                TODO("Not yet implemented")
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