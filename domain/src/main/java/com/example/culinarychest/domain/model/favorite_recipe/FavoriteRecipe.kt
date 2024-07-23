package com.example.culinarychest.domain.model.favorite_recipe

data class FavoriteRecipe(
    val favoriteRecipeId: Int,
    val id: String,
    val recipeId: Int,
    val addedDate: String,
)