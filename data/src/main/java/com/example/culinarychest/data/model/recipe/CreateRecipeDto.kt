package com.example.culinarychest.data.model.recipe

import com.google.gson.annotations.SerializedName
import java.io.File

data class CreateRecipeDto(
    @SerializedName("RecipeImage", alternate = ["recipeImage"])
    val recipeImage: File?,
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("Steps", alternate = ["steps"])
    val steps: List<String>,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String
)