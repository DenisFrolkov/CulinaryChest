package com.example.culinarychest.domain.repository

import com.example.culinarychest.domain.model.application_user.Token
import com.example.culinarychest.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.model.recipe.Recipe
import com.example.culinarychest.domain.model.recipe.RecipeRequest
import com.example.culinarychest.domain.model.recipe.UpdateRecipe

interface ApplicationUserRecipeRepository {

    suspend fun getUserRecipes(token: Token): List<Recipe>

    suspend fun deleteUserRecipe(recipeRequest: RecipeRequest)

    suspend fun createUserRecipe(
        createRecipe: CreateRecipe
    )

    suspend fun updateUserRecipe(
        updateRecipe: UpdateRecipe
    )
}