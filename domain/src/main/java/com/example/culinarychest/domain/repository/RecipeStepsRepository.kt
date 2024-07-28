package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.step.CreateStep
import com.example.culinarychest.domain.model.step.DeleteStep
import com.example.culinarychest.domain.model.step.Step
import com.example.culinarychest.domain.model.step.UpdateStep

interface RecipeStepsRepository {
    suspend fun getRecipeSteps(recipeRequest: RecipeRequest): List<Step>
    suspend fun createRecipeStep(createStep: CreateStep)
    suspend fun updateRecipeStep(updateStep: UpdateStep)
    suspend fun deleteRecipeStep(deleteStep: DeleteStep)
}