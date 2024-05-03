package com.example.culinarychest.domain.domain.dataclasses.favorite_recipe

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class FavoriteRecipe(
    @SerializedName("FavoriteRecipeId", alternate = ["favoriteRecipeId"])
    val favoriteRecipeId: Int,
    @SerializedName("Id", alternate = ["id"])
    val id: String,
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: Int,
    @SerializedName("AddedDate", alternate = ["addedDate"])
    val addedDate: String,
)