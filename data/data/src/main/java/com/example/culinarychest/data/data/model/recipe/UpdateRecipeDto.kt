package com.example.culinarychest.data.data.model.recipe

import com.google.gson.annotations.SerializedName

data class UpdateRecipeDto(
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String,
    @SerializedName("SavedCount", alternate = ["savedCount"])
    val savedCount: Int
)