package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName

data class FavoriteRecipe(
    @SerializedName("FavoriteRecipeId")
    val favoriteRecipeId: Int,
    @SerializedName("Id")
    val id: String,
    @SerializedName("RecipeId")
    val recipeId: Int,
    @SerializedName("AddedDate")
    val addedDate: String,
)