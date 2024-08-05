package com.den.culinarychest.presentation.ui.main.viewmodel.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.usecase.GetRecipePhotoUseCase
import com.example.culinarychest.domain.usecase.tokenUseCase.GetTokenUseCase
import com.example.culinarychest.domain.usecase.userRecipeUseCases.GetUserRecipesUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatedViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val getRecipePhotoUseCase: GetRecipePhotoUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
) : ViewModel() {

    private val _listRecipesUser = MutableStateFlow<List<Recipe>>(emptyList())
    val listRecipesUser = _listRecipesUser.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private var getToken: String? = null

    init {
        viewModelScope.launch {
            getToken = getTokenUseCase.invoke().toString()
        }
    }

    fun getListRecipesUser() {
        viewModelScope.launch {
            getToken?.let {
                getUserRecipesUseCase(Token(token = it))
                    .collectLatest { result ->
                        when (result) {
                            is ProcessingResult.Error -> {
                                _showErrorToastChannel.send(true)
                            }

                            is ProcessingResult.Success -> {
                                result.data?.let { applicationUserRecipes ->
                                    _listRecipesUser.update { applicationUserRecipes }
                                    loadImagesForCreatedRecipes(applicationUserRecipes)
                                }
                            }
                            is ProcessingResult.Loading -> {

                            }
                        }
                    }
            }
        }
    }

    private fun loadImagesForCreatedRecipes(favoriteRecipes: List<Recipe>) {
        favoriteRecipes.forEach { recipe ->
            viewModelScope.launch {
                try {
                    val url = getRecipePhotoUseCase(recipe.imageUrl)
                    _listRecipesUser.update { currentRecipes ->
                        currentRecipes.map {
                            if (it.imageUrl == recipe.imageUrl) {
                                it.copy(imageUrl = url)
                            } else {
                                it
                            }
                        }
                    }
                } catch (e: Exception) {

                }
            }
        }
    }
}