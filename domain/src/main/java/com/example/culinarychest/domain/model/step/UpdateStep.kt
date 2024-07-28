package com.example.culinarychest.domain.model.step

import com.example.culinarychest.domain.model.application_user.Token


data class UpdateStep(
    val token: Token,
    val recipeId: String,
    val stepId: String,
    val stepData: StepData
)
