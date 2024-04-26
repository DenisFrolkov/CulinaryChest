package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe

interface ApplicationUserRecipeRepository {

    suspend fun getApplicationUserRecipes(): ProcessingResult<List<Recipe>>
    suspend fun createApplicationUserRecipe(): ProcessingResult<List<Recipe>>
    suspend fun updateApplicationUserRecipe(): ProcessingResult<Recipe>
    suspend fun deleteApplicationUserRecipe(): ProcessingResult<Recipe>

}