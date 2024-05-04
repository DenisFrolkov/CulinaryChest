package com.example.culinarychest.domain.domain.model.recipe

import com.example.culinarychest.domain.domain.model.step.Step
import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: String,
    @SerializedName("Id", alternate = ["id"])
    val id: String,
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("RecipeImage", alternate = ["recipeImage"])
    val recipeImage: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("Steps", alternate = ["steps"])
    val steps: List<Step>,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String,
    @SerializedName("SavedCount", alternate = ["savedCount"])
    val savedCount: Int
)
