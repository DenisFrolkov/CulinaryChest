package com.example.culinarychest.domain.model.favorite_recipe

import com.example.culinarychest.domain.model.application_user.Token

data class CreateFavoriteRecipe(
    val token: Token,
    val recipeId: Int,
    val addedDate: AddedDateFavoriteRecipe
)