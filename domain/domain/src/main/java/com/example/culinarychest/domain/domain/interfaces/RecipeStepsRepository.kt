package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Step

interface RecipeStepsRepository {
    suspend fun getRecipeSteps(): ProcessingResult<List<Step>>
    suspend fun createRecipeStep(): ProcessingResult<Step>
    suspend fun updateRecipeStep(): ProcessingResult<Step>
    suspend fun deleteRecipeStep(): ProcessingResult<Step>
}