package com.example.culinarychest.data.model.favorite_recipe

import com.google.gson.annotations.SerializedName

data class CreateFavoriteRecipeDto(
    @SerializedName("addedDate")
    val addedDate: String
)