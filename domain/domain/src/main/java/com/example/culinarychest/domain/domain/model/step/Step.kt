package com.example.culinarychest.domain.domain.model.step

import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("StepId", alternate = ["stepId"])
    val stepId: Int,
    @SerializedName("Description", alternate = ["description"])
    val description: String,
    @SerializedName("Order", alternate = ["order"])
    val order: Int,
    @SerializedName("RecipeId", alternate = ["recipeId"])
    val recipeId: Int
)