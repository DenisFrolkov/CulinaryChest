package com.example.culinarychest.domain.domain.dataclasses.favorite_recipe

import com.google.gson.annotations.SerializedName

data class CreateFavoriteRecipe(
    @SerializedName("addedDate")
    val addedDate: String
)