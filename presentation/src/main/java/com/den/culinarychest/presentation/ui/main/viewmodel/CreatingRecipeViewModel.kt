package com.den.culinarychest.presentation.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.CreateApplicationUserRecipeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File

class CreatingRecipeViewModel(
    private val createApplicationUserRecipeUseCase: CreateApplicationUserRecipeUseCase,
) : ViewModel() {

    private val _createdRecipeResult = MutableLiveData<ProcessingResult<CreateRecipe>>()
    val createdRecipeResult: LiveData<ProcessingResult<CreateRecipe>> = _createdRecipeResult

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun createRecipeUser(
        token: String,
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: String,
        creationDate: String,
        preparationTime: String
    ) {
        viewModelScope.launch {
            try {
                createApplicationUserRecipeUseCase(
                    token, title, recipeImage, ingredients, steps, creationDate, preparationTime
                )
                _createdRecipeResult.value = ProcessingResult.Success(null)
            } catch (e: Exception) {
                _createdRecipeResult.value =
                    ProcessingResult.Error(message = "Error create recipe: ${e.message}")
                _showErrorToastChannel.send(true)
            }
        }
    }
}