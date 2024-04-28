package com.den.culinarychest.presentation.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.interfaces.ApplicationUserFavoriteRecipeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApplicationUserFavoriteRecipeViewModel(
    private val favoriteRecipeRepository: ApplicationUserFavoriteRecipeRepository,
    private val applicationUserViewModel: ApplicationUserViewModel
) : ViewModel() {

    private val _userFavoriteRecipes = MutableStateFlow<List<FavoriteRecipe>>(emptyList())
    val userFavoriteRecipes = _userFavoriteRecipes.asStateFlow()

    private val _createFavoriteRecipe = MutableLiveData<ProcessingResult<FavoriteRecipe>>()
    val createFavoriteRecipe = _createFavoriteRecipe

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    init {
        applicationUserViewModel.token.observeForever { token ->
            if (token != null) {
                getApplicationUserFavoriteRecipes(token = token)
            }
        }
    }

    fun getApplicationUserFavoriteRecipes(token: String) {
        viewModelScope.launch {
            favoriteRecipeRepository.getApplicationUserFavoriteRecipes(token).collectLatest { result ->
                when(result) {
                    is ProcessingResult.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is ProcessingResult.Success -> {
                        result.data?.let { favoriteRecipe ->
                            _userFavoriteRecipes.update { favoriteRecipe }
                        }
                    }
                }
            }
        }
    }
    fun createApplicationUserFavoriteRecipes() {

    }
    fun deleteApplicationUserFavoriteRecipe() {

    }

}