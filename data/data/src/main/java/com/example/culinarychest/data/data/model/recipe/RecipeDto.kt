package com.example.culinarychest.data.data.model.recipe

import com.example.culinarychest.data.data.model.step.StepDto
import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: String,
    @SerializedName("Id", alternate = ["id"])
    val id: String,
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("recipeImage", alternate = ["RecipeImage"])
    val imageUrl: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("Steps", alternate = ["steps"])
    val steps: List<StepDto>,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String,
    @SerializedName("SavedCount", alternate = ["savedCount"])
    val savedCount: Int
)
