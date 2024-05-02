package com.example.culinarychest.data.data.api

import com.example.culinarychest.domain.domain.dataclasses.ApplicationUser
import com.example.culinarychest.domain.domain.dataclasses.ApplicationUserInfo
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Login
import com.example.culinarychest.domain.domain.dataclasses.recipe.Recipe
import com.example.culinarychest.domain.domain.dataclasses.step.Step
import com.example.culinarychest.domain.domain.dataclasses.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.dataclasses.Token
import com.example.culinarychest.domain.domain.dataclasses.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.dataclasses.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.dataclasses.step.CreateStep
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
    suspend fun registrationApplicationUser(@Body applicationUser: ApplicationUser) //Completed
    @POST("/api/authentication/login")
    suspend fun authorizationApplicationUser(@Body login: Login): Response<Token> //Completed
    @GET("/api/authentication/user")
    suspend fun getApplicationUserInfo(@Header("Authorization") token: String): ApplicationUserInfo //Completed

    @GET("/api/applicationUser/favoriteRecipe")
    suspend fun getApplicationUserFavoriteRecipes(@Header("Authorization") token: String): List<FavoriteRecipe> //Completed
    @POST("/api/applicationUser/favoriteRecipe/{recipeId}")
    suspend fun createApplicationUserFavoriteRecipes(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: Int,
        @Body addedDate: CreateFavoriteRecipe
    ) //Completed
    @DELETE("/api/applicationUser/favoriteRecipe/{favoriteRecipeId}")
    suspend fun deleteApplicationUserFavoriteRecipe(@Header("Authorization") token: String, @Path("favoriteRecipeId") favoriteRecipeId: String) //Completed

    @GET("/api/recipe")
    suspend fun getRecipes(@Header("Authorization") token: String): List<Recipe> //Completed
    @GET("/api/recipe/{recipeId}")
    suspend fun getRecipeById(@Header("Authorization") token: String, @Path("recipeId") recipeId: String): List<Recipe> //Completed

    @GET("/api/applicationUser/recipe")
    suspend fun getApplicationUserRecipes(@Header ("Authorization") token: String): List<Recipe> //Completed
    @POST("/api/applicationUser/recipe")
    suspend fun createApplicationUserRecipe(@Header ("Authorization") token: String, @Body recipe: CreateRecipe) //Completed
    @PUT("/api/applicationUser/Recipe/{recipeId}")
    suspend fun updateApplicationUserRecipe(@Header ("Authorization") token: String, @Path("recipeId") recipeId: String, @Body recipe: UpdateRecipe) //Completed
    @DELETE("/api/applicationUser/Recipe/{recipeId}")
    suspend fun deleteApplicationUserRecipe(@Header("Authorization") token: String, @Path("recipeId") recipeId: String) //Completed

    @GET("/api/recipe/{recipeId}/steps")
    suspend fun getRecipeSteps(@Header ("Authorization") token: String, @Path("recipeId") recipeId: String): List<Step> //Completed
    @POST("/api/recipe/{recipeId}/steps")
    suspend fun createRecipeStep(@Header ("Authorization") token: String, @Path("recipeId") recipeId: String, @Body step: CreateStep) //Completed
    @PUT("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun updateRecipeStep(@Header ("Authorization") token: String, @Path("recipeId") recipeId: String, @Path("stepId") stepId: String, @Body updateStep: CreateStep) //Completed
    @DELETE("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun deleteRecipeStep(@Header ("Authorization") token: String, recipeId: String, stepId: String) //Completed
}