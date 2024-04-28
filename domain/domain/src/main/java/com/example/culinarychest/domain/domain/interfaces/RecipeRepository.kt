package com.example.culinarychest.domain.domain.interfaces

import com.example.culinarychest.domain.domain.ProcessingResult
import com.example.culinarychest.domain.domain.dataclasses.Recipe
import com.example.culinarychest.domain.domain.dataclasses.Token
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun getRecipeById(recipeId: String): Flow<ProcessingResult<Recipe>>

}