package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserRecipeRepository {

    suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun createApplicationUserRecipe(token: String, recipe: CreateRecipe)
    suspend fun updateApplicationUserRecipe(): Flow<ProcessingResult<Recipe>>
    suspend fun deleteApplicationUserRecipe(): Flow<ProcessingResult<Recipe>>

}