package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import kotlinx.coroutines.flow.Flow

interface ApplicationUserRecipeRepository {

    suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun createApplicationUserRecipe(token: String, recipe: CreateRecipe)
    suspend fun updateApplicationUserRecipe(token: String, recipeId: String, recipe: UpdateRecipe)
    suspend fun deleteApplicationUserRecipe(token: String, recipeId: String)

}