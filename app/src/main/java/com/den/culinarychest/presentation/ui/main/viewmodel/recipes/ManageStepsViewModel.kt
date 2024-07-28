package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.den.culinarychest.presentation.ui.other.common.model.AddStep
import com.den.culinarychest.presentation.ui.other.common.model.AddStepCreate
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.DeleteStep
import com.example.culinarychest.domain.model.step.StepData
import com.example.culinarychest.domain.model.step.UpdateStep
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.CreateStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.DeleteStepUseCase
import com.example.culinarychest.domain.usecase.recipeStepsUseCases.UpdateStepUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ManageStepsViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val createStepUseCase: CreateStepUseCase,
    private val deleteStepUseCase: DeleteStepUseCase,
    private val updateStepUseCase: UpdateStepUseCase
) : ViewModel() {

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun createStepsRecipe(recipeId: String, step: AddStepCreate) {
        viewModelScope.launch {
            try {
                getToken?.let { createStepUseCase(CreateStep(token = Token(it), recipeId = recipeId, stepData = StepData(step.textRecipeStep, step.numberTextRecipeStep))) }
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }

    fun updateStepRecipe(recipeId: String, step: AddStep) {
        viewModelScope.launch {
            try {
                getToken?.let { updateStepUseCase(UpdateStep(Token(it), recipeId, step.stepId, StepData(step.textRecipeStep, step.numberTextRecipeStep))) }
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
    fun deleteStepRecipe(recipeId: String, stepId: String) {
        viewModelScope.launch {
            try {
                getToken?.let { deleteStepUseCase(DeleteStep(Token(it), recipeId, stepId)) }
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }

        }
    }
}