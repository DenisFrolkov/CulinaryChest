package com.example.culinarychest.domain.domain.dataclasses.favorite_recipe

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

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