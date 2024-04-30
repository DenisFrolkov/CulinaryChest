package com.example.culinarychest.domain.domain.dataclasses

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.time.LocalDateTime

data class BodyRequest(
    @SerializedName("addedDate")
    val addedDate: String = LocalDateTime.now().toString()
)