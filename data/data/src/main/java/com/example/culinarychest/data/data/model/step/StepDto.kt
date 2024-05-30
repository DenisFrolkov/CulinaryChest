package com.example.culinarychest.data.data.model.step

import com.google.gson.annotations.SerializedName

data class StepDto(
    @SerializedName("StepId", alternate = ["stepId"])
    val stepId: Int,
    @SerializedName("Description", alternate = ["description"])
    val description: String,
    @SerializedName("Order", alternate = ["order"])
    val order: Int,
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: Int
)