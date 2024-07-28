package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.CreateUserRecipeUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File

class CreatingRecipeViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val createUserRecipeUseCase: CreateUserRecipeUseCase,
) : ViewModel() {

    private val _createdRecipeResult = MutableLiveData<ProcessingResult<CreateRecipe>>()
    val createdRecipeResult: LiveData<ProcessingResult<CreateRecipe>> = _createdRecipeResult

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun createRecipeUser(
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: List<String>,
        creationDate: String,
        preparationTime: String
    ) {
        viewModelScope.launch {
            try {
                getToken?.let {
                    createUserRecipeUseCase(
                        CreateRecipe(Token(it), recipeImage, title, ingredients, steps, creationDate, preparationTime)
                    )
                }
                _createdRecipeResult.value = ProcessingResult.Success(null)
            } catch (e: Exception) {
                _createdRecipeResult.value =
                    ProcessingResult.Error(message = "Error create recipe: ${e.message}")
                _showErrorToastChannel.send(true)
            }
        }
    }
}