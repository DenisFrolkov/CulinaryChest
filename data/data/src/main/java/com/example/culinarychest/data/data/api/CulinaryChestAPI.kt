package com.example.culinarychest.data.data.api

import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUserInfo
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Login
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.step.Step
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val BASE_URL = "https://10.0.2.2:7286"

interface CulinaryChestAPI {

    @POST("/api/authentication/register")
    suspend fun registrationApplicationUser(@Body applicationUser: ApplicationUser)
    @POST("/api/authentication/login")
    suspend fun authorizationApplicationUser(@Body login: Login): Response<Token>
    @GET("/api/authentication/user")
    suspend fun getApplicationUserInfo(@Header("Authorization") token: String): ApplicationUserInfo

    @GET("/api/applicationUser/favoriteRecipe")
    suspend fun getApplicationUserFavoriteRecipes(@Header("Authorization") token: String): List<FavoriteRecipe>
    @POST("/api/applicationUser/favoriteRecipe/{recipeId}")
    suspend fun createApplicationUserFavoriteRecipes(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: Int,
        @Body addedDate: CreateFavoriteRecipe
    )
    @DELETE("/api/applicationUser/favoriteRecipe/{favoriteRecipeId}")
    suspend fun deleteApplicationUserFavoriteRecipe(): FavoriteRecipe

    @GET("/api/recipe")
    suspend fun getRecipes(@Header("Authorization") token: String): List<Recipe>
    @GET("/api/recipe/{recipeId}")
    suspend fun getRecipeById(@Header("Authorization") token: String, @Path("recipeId") recipeId: String): List<Recipe>

    @GET("/api/applicationUser/recipe")
    suspend fun getApplicationUserRecipes(@Header("Authorization") token: String): List<Recipe>
    @POST("/api/applicationUser/recipe")
    suspend fun createApplicationUserRecipe() : String
    @PUT("/api/applicationUser/Recipe/{recipeId}")
    suspend fun updateApplicationUserRecipe(): Recipe
    @DELETE("/api/applicationUser/Recipe/{recipeId}")
    suspend fun deleteApplicationUserRecipe(): Recipe

    @GET("/api/recipe/{recipeId}/steps")
    suspend fun getRecipeSteps(): List<Step>
    @POST("/api/recipe/{recipeId}/steps")
    suspend fun createRecipeStep(): Step
    @PUT("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun updateRecipeStep(): Step
    @DELETE("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun deleteRecipeStep(): Step
}