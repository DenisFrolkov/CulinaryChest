package com.example.culinarychest.data.model.step

import com.google.gson.annotations.SerializedName

data class CreateStepDto(
    @SerializedName("Description", alternate = ["description"])
    val description: String,
    @SerializedName("Order", alternate = ["order"])
    val order: String,
)