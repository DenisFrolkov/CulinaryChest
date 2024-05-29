package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import kotlinx.coroutines.flow.Flow

interface ApplicationUserRecipeRepository {

    suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>
    suspend fun createApplicationUserRecipe(token: String, title: String, recipeImage: String, ingredients: String, step: List<CreateStep>, creationDate: String, preparationTime: String)
    suspend fun updateApplicationUserRecipe(token: String, recipeId: String, recipe: UpdateRecipe)
    suspend fun deleteApplicationUserRecipe(token: String, recipeId: String)

}