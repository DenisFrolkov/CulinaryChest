package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.usecase.applicationUserRecipeUseCases.GetApplicationUserRecipesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatedViewModel(
    private val getApplicationUserRecipesUseCase: GetApplicationUserRecipesUseCase,
) : ViewModel() {

    private val _listRecipesUser = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipesUser = _listRecipesUser.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    fun getListRecipesUser(token: String) {
        viewModelScope.launch {
            getApplicationUserRecipesUseCase(token)
                .collectLatest { result ->
                    when (result) {
                        is ProcessingResult.Error -> {
                            _showErrorToastChannel.send(true)
                        }

                        is ProcessingResult.Success -> {
                            result.data?.let { applicationUserRecipes ->
                                _listRecipesUser.update { applicationUserRecipes }
                            }
                        }
                    }
                }
        }
    }
}