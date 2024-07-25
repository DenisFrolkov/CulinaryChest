package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.DeleteUserRecipeUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.UpdateUserRecipeUseCase
import kotlinx.coroutines.launch
import java.io.File

class ManageRecipeUserViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val updateUserRecipeUseCase: UpdateUserRecipeUseCase,
    private val deleteUserRecipeUseCase: DeleteUserRecipeUseCase
) : ViewModel() {

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun updateRecipeUser(
        recipeId: String,
        title: String,
        recipeImage: File? = null,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    ) {
        viewModelScope.launch {
            try {
                getToken?.let {
                    updateUserRecipeUseCase(
                        it, recipeId, title, recipeImage, ingredients, creationDate, preparationTime
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

    fun deleteRecipeUser(recipeId: String) {
        viewModelScope.launch {
            try {
                getToken?.let {
                    deleteUserRecipeUseCase(
                        it,
                        recipeId
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

}