package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.ProcessingResult
import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.Step
import kotlinx.coroutines.flow.Flow

interface RecipeStepsRepository {
    suspend fun getRecipeSteps(token: String, recipeId: String): Flow<ProcessingResult<List<Step>>>
    suspend fun createRecipeStep(token: String, recipeId: String, step: CreateStep)
    suspend fun updateRecipeStep(token: String, recipeId: String, stepId: String, updateStep: CreateStep)
    suspend fun deleteRecipeStep(token: String, recipeId: String, stepId: String)
}