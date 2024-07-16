package com.den.culinarychest.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.domain.usecase.recipeStepsUseCases.UpdateStepUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ManageStepsViewModel(
    private val createStepUseCase: CreateStepUseCase,
    private val deleteStepUseCase: DeleteStepUseCase,
    private val updateStepUseCase: UpdateStepUseCase
) : ViewModel() {

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun createRecipeSteps(token: String, recipeId: String, step: CreateStep) {
        viewModelScope.launch {
            try {
                createStepUseCase(token, recipeId, step)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep) {
        viewModelScope.launch {
            try {
                updateStepUseCase(token, recipeId, stepId, updateStep)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
    fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        viewModelScope.launch {
            try {
                deleteStepUseCase(token, recipeId, stepId)
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