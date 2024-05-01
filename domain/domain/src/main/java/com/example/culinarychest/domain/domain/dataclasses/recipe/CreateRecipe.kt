package com.example.culinarychest.domain.domain.dataclasses.recipe

import com.example.culinarychest.domain.domain.dataclasses.step.CreateStep
import com.example.culinarychest.domain.domain.dataclasses.step.Step
import com.google.gson.annotations.SerializedName

data class CreateRecipe(
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("RecipeImage", alternate = ["recipeImage"])
    val recipeImage: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("Steps", alternate = ["steps"])
    val steps: List<CreateStep>,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String,
    @SerializedName("SavedCount", alternate = ["savedCount"])
    val savedCount: Int
)