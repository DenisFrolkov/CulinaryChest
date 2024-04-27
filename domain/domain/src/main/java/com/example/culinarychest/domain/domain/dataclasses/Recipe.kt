package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("RecipeId")
    val recipeId: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("RecipeImage")
    val recipeImage: String,
    @SerializedName("Ingredients")
    val ingredients: String,
    @SerializedName("Steps")
    val steps: List<Step>,
    @SerializedName("CreationDate")
    val creationDate: String,
    @SerializedName("PreparationTime")
    val preparationTime: String,
    @SerializedName("SavedCount")
    val savedCount: Int
)
