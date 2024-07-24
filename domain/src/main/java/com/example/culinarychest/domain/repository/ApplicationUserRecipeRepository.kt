package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.recipe.Recipe
import java.io.File

interface ApplicationUserRecipeRepository {

    suspend fun getUserRecipes(token: String): List<Recipe>

    suspend fun deleteUserRecipe(token: String, recipeId: String)

    suspend fun createUserRecipe(
        token: String,
        title: String,
        recipeImage: File,
        ingredients: String,
        steps: String,
        creationDate: String,
        preparationTime: String
    )

    suspend fun updateUserRecipe(
        token: String,
        recipeId: String,
        title: String,
        recipeImage: File? = null,
        ingredients: String,
        creationDate: String,
        preparationTime: String
    )
}