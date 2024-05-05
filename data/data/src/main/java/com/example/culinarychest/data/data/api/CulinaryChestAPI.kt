package com.example.culinarychest.data.data.api

import com.example.culinarychest.domain.domain.model.ApplicationUser
import com.example.culinarychest.domain.domain.model.ApplicationUserInfo
import com.example.culinarychest.domain.domain.model.favorite_recipe.FavoriteRecipe
import com.example.culinarychest.domain.domain.model.Login
import com.example.culinarychest.domain.domain.model.recipe.Recipe
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.model.favorite_recipe.CreateFavoriteRecipe
import com.example.culinarychest.domain.domain.model.Token
import com.example.culinarychest.domain.domain.model.recipe.CreateRecipe
import com.example.culinarychest.domain.domain.model.recipe.UpdateRecipe
import com.example.culinarychest.domain.domain.model.step.CreateStep
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/api/recipe/get-recipes")
    suspend fun getRecipes(@Header("Authorization") token: String): List<Recipe> //Completed
    @GET("/api/recipe") // Изменил путь запроса на "/api/recipes", чтобы получить список рецептов
    suspend fun getRecipeById(@Header("Authorization") token: String, @Query("recipeIds") recipeIds: List<String>): List<Recipe> // Изменил параметр recipeId на recipeIds и использовал аннотацию @Query для передачи списка идентификаторов

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