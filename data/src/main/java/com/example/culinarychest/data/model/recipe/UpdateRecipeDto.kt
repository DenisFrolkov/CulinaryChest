package com.example.culinarychest.data.model.recipe

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateRecipeDto(
    @SerializedName("Title", alternate = ["title"])
    val title: String,
    @SerializedName("RecipeImage", alternate = ["recipeImage"])
    val recipeImage: File? = null,
    @SerializedName("Ingredients", alternate = ["ingredients"])
    val ingredients: String,
    @SerializedName("CreationDate", alternate = ["creationDate"])
    val creationDate: String,
    @SerializedName("PreparationTime", alternate = ["preparationTime"])
    val preparationTime: String,
    @SerializedName("SavedCount", alternate = ["savedCount"])
    val savedCount: Int
)