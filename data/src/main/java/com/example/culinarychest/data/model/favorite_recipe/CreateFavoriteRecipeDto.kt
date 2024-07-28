package com.example.culinarychest.data.model.favorite_recipe

import com.example.culinarychest.data.model.application_user.TokenDto
import com.google.gson.annotations.SerializedName

data class CreateFavoriteRecipeDto(
    val token: TokenDto,
    val recipeId: Int,
    @SerializedName("addedDate")
    val addedDate: String
)