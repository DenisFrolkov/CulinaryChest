package com.den.culinarychest.presentation.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GenericViewModelFactory<T : ViewModel>(private val creator: () -> T) : ViewModelProvider.Factory {
    override fun <U : ViewModel> create(modelClass: Class<U>): U {
        if (modelClass.isAssignableFrom(creator().javaClass)) {
            @Suppress("UNCHECKED_CAST")
            return creator() as U
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

