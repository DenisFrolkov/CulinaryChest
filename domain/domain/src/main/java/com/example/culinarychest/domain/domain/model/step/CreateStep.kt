package com.example.culinarychest.domain.domain.model.step

import com.google.gson.annotations.SerializedName

data class CreateStep(
    @SerializedName("Description", alternate = ["description"])
    val description: String,
    @SerializedName("Order", alternate = ["order"])
    val order: String,
)