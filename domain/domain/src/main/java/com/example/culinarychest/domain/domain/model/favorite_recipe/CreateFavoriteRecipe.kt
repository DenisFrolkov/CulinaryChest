package com.example.culinarychest.domain.domain.model.favorite_recipe

import com.google.gson.annotations.SerializedName

data class CreateFavoriteRecipe(
    @SerializedName("addedDate")
    val addedDate: String
)