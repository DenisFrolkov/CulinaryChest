package com.example.culinarychest.domain.domain.repository

import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ApplicationUserRecipeRepository {

    suspend fun getApplicationUserRecipes(token: String): Flow<ProcessingResult<List<Recipe>>>

    suspend fun deleteApplicationUserRecipe(token: String, recipeId: String)

    suspend fun createApplicationUserRecipe(
        token: String,
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: String,
        creationDate: String,
        preparationTime: String
    )

    suspend fun updateApplicationUserRecipe(
        token: String,
        recipeId: String,
        title: String,
        recipeImage: File? = null,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    )
}