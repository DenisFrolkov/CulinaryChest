package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("StepId")
    val stepId: Int,
    @SerializedName("Description")
    val description: String,
    @SerializedName("Order")
    val order: Int,
    @SerializedName("RecipeId")
    val recipeId: Int
)