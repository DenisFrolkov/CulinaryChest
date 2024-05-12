package com.example.culinarychest.domain.domain.model.step

import com.google.gson.annotations.SerializedName

data class UpdateStep(
    val stepId: String,
    val description: String,
    val order: String,
)
