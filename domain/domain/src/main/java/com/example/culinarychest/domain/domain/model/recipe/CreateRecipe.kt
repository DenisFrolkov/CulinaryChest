package com.example.culinarychest.domain.domain.model.recipe

import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.google.gson.annotations.SerializedName

data class CreateRecipe(
    @SerializedName("RecipeImage", alternate = ["recipeImage"])
    val recipeImage: String,
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("Steps", alternate = ["steps"])
    val steps: List<CreateStep>,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String
)