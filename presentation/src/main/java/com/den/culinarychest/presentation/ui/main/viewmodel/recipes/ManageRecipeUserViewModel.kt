package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.usecase.userRecipeUseCases.DeleteUserRecipeUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.UpdateUserRecipeUseCase
import kotlinx.coroutines.launch
import java.io.File

class ManageRecipeUserViewModel(
    private val updateUserRecipeUseCase: UpdateUserRecipeUseCase,
    private val deleteUserRecipeUseCase: DeleteUserRecipeUseCase
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
                updateUserRecipeUseCase(
                    token, recipeId, title, recipeImage, ingredients, creationDate, preparationTime
                )
            } catch (e: Exception) {

            }
        }
    }

    fun deleteRecipeUser(token: String, recipeId: String) {
        viewModelScope.launch {
            try {
                deleteUserRecipeUseCase(
                    token,
                    recipeId
                )
            } catch (e: Exception) {

            }
        }
    }

}