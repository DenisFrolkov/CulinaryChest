package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Step
import kotlinx.coroutines.flow.Flow

interface RecipeStepsRepository {
    suspend fun getRecipeSteps(): Flow<ProcessingResult<List<Step>>>
    suspend fun createRecipeStep(): Flow<ProcessingResult<Step>>
    suspend fun updateRecipeStep(): Flow<ProcessingResult<Step>>
    suspend fun deleteRecipeStep(): Flow<ProcessingResult<Step>>
}