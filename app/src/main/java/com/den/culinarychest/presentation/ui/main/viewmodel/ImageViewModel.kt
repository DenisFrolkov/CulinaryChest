package com.den.culinarychest.presentation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.culinarychest.domain.usecase.GetRecipePhotoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImageViewModel(
    private val getRecipePhotoUseCase: GetRecipePhotoUseCase
) : ViewModel() {

    private val _photoUrl = MutableStateFlow<String?>(null)
    val photoUrl: StateFlow<String?> get() = _photoUrl

    fun fetchRecipePhoto(imageId: String) {
        viewModelScope.launch {
            try {
                val url = getRecipePhotoUseCase(imageId)
                _photoUrl.value = url
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}