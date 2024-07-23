package com.example.culinarychest.data.model.favorite_recipe

import com.google.gson.annotations.SerializedName

data class FavoriteRecipeDto(
    @SerializedName("FavoriteRecipeId", alternate = ["favoriteRecipeId"])
    val favoriteRecipeId: Int,
    @SerializedName("Id", alternate = ["id"])
    val id: String,
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: Int,
    @SerializedName("AddedDate", alternate = ["addedDate"])
    val addedDate: String,
)