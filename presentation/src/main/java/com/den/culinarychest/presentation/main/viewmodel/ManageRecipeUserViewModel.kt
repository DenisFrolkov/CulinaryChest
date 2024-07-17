package com.den.culinarychest.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.DeleteApplicationUserRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.UpdateApplicationUserRecipeUseCase
import kotlinx.coroutines.launch
import java.io.File

class ManageRecipeUserViewModel(
    private val updateApplicationUserRecipeUseCase: UpdateApplicationUserRecipeUseCase,
    private val deleteApplicationUserRecipeUseCase: DeleteApplicationUserRecipeUseCase
) : ViewModel() {

    fun updateRecipeUser(
        token: String,
        recipeId: String,
        title: String,
        recipeImage: File? = null,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    ) {
        viewModelScope.launch {
            try {
                updateApplicationUserRecipeUseCase(
                    token, recipeId, title, recipeImage, ingredients, creationDate, preparationTime
                )
            } catch (e: Exception) {

            }
        }
    }

    fun deleteRecipeUser(token: String, recipeId: String) {
        viewModelScope.launch {
            try {
                deleteApplicationUserRecipeUseCase(
                    token,
                    recipeId
                )
            } catch (e: Exception) {

            }
        }
    }

}