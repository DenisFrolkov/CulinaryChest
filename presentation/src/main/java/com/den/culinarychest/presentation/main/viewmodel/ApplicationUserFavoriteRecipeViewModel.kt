package com.den.culinarychest.presentation.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.CreateApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.DeleteApplicationUserFavoriteRecipeUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetApplicationUserFavoriteRecipesUseCase
import com.example.culinarychest.domain.domain.usecase.applicationUserFavoriteRecipeUseCases.GetFavoriteRecipeByRecipeIdUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationUserFavoriteRecipeViewModel(
    private val getApplicationUserFavoriteRecipesUseCase: GetApplicationUserFavoriteRecipesUseCase,
    private val getFavoriteRecipeByRecipeIdUseCase: GetFavoriteRecipeByRecipeIdUseCase,
    private val createApplicationUserFavoriteRecipesUseCase: CreateApplicationUserFavoriteRecipesUseCase,
    private val deleteApplicationUserFavoriteRecipeUseCase: DeleteApplicationUserFavoriteRecipeUseCase
) : ViewModel() {

    private val _userDtoFavoriteRecipes = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val userFavoriteRecipes = _userDtoFavoriteRecipes.asStateFlow()

    private val _favoriteRecipeByRecipeId = MutableStateFlow<FavoriteRecipe?>(null)
    val favoriteRecipeByRecipeId = _favoriteRecipeByRecipeId.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getApplicationUserFavoriteRecipes(token: String) {
        viewModelScope.launch {
            getApplicationUserFavoriteRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipe ->
                                _userDtoFavoriteRecipes.update { favoriteRecipe }
                            }
                        }
                    }
                }
        }
    }

    fun getFavoriteRecipeByRecipeId(token: String, recipeId: String) {
        viewModelScope.launch {
            getFavoriteRecipeByRecipeIdUseCase(token, recipeId)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _favoriteRecipeByRecipeId.update { null }
                        }
                        is ProcessingResult.Success -> {
                            result.data?.let { favoriteRecipeByRecipeId ->
                                _favoriteRecipeByRecipeId.update { favoriteRecipeByRecipeId }
                            }
                        }
                    }
                }
        }
    }

    fun createApplicationUserFavoriteRecipes(
        token: String,
        recipeId: Int,
        addedDate: CreateFavoriteRecipe
    ) {
        viewModelScope.launch {
            try {
                createApplicationUserFavoriteRecipesUseCase(
                    token,
                    recipeId,
                    addedDate
                )
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    fun deleteApplicationUserFavoriteRecipe(token: String, recipeId: String) {
        viewModelScope.launch {
            try {
                deleteApplicationUserFavoriteRecipeUseCase(
                    token,
                    recipeId
                )
            } catch (e: Exception) {

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