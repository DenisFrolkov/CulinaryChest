package com.example.culinarychest.data.api

import com.example.culinarychest.data.model.application_user.DuplicationUserInfoDto
import com.example.culinarychest.data.model.application_user.LoginDto
import com.example.culinarychest.data.model.application_user.TokenDto
import com.example.culinarychest.data.model.application_user.UserDto
import com.example.culinarychest.data.model.application_user.UserInfoDto
import com.example.culinarychest.data.model.favorite_recipe.FavoriteRecipeDto
import com.example.culinarychest.data.model.recipe.RecipeDto
import com.example.culinarychest.data.model.step.StepDataDto
import com.example.culinarychest.data.model.step.StepDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CulinaryChestAPI {

    @POST("/api/authentication/register")
    suspend fun registrationUser(@Body userDto: UserDto): Response<DuplicationUserInfoDto>

    @POST("/api/authentication/login")
    suspend fun authorizationUser(@Body loginDto: LoginDto): Response<TokenDto>

    @GET("/api/authentication/user")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfoDto

    @GET("/api/applicationUser/favoriteRecipe")
    suspend fun getListFavoriteRecipesUser(@Header("Authorization") token: String): List<FavoriteRecipeDto>

    @GET("/api/applicationUser/favoriteRecipe/{recipeId}")
    suspend fun getFavoriteRecipeByRecipeId(
        @Header("Authorization") token: String, @Path("recipeId") recipeId: String
    ): FavoriteRecipeDto

    @POST("/api/applicationUser/favoriteRecipe/{recipeId}")
    suspend fun createFavoriteRecipesUser(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: Int,
        @Body addedDate: String
    )

    @DELETE("/api/applicationUser/favoriteRecipe/{recipeId}")
    suspend fun deleteFavoriteRecipeUser(
        @Header("Authorization") token: String, @Path("recipeId") recipeId: String
    )

    @GET("/api/recipe/listRecipe")
    suspend fun getListRecipes(
        @Header("Authorization") token: String, @Query("SearchTerm") searchTerm: String?
    ): List<RecipeDto>

    @GET("/api/recipe/listRecipeByIds")
    suspend fun getListRecipeByIds(
        @Header("Authorization") token: String, @Query("recipeIds") recipeIds: List<String>
    ): List<RecipeDto>

    @GET("/api/recipe/{recipeId}")
    suspend fun getRecipeById(
        @Header("Authorization") token: String, @Path("recipeId") recipeId: String
    ): List<RecipeDto>


    @GET("/api/applicationUser/recipe")
    suspend fun getListRecipesUser(@Header("Authorization") token: String): List<RecipeDto>

    @Multipart
    @POST("/api/applicationUser/recipe")
    suspend fun createRecipeUser(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part recipeImage: MultipartBody.Part,
        @Part("ingredients") ingredients: RequestBody,
        @Part("steps") steps: RequestBody,
        @Part("creationDate") creationDate: RequestBody,
        @Part("preparationTime") preparationTime: RequestBody
    )

    @Multipart
    @PUT("/api/applicationUser/Recipe/{recipeId}")
    suspend fun updateRecipeUser(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: String,
        @Part("Title") title: RequestBody,
        @Part recipeImage: MultipartBody.Part? = null,
        @Part("Ingredients") ingredients: RequestBody,
        @Part("CreationDate") creationDate: RequestBody,
        @Part("preparationTime") preparationTime: RequestBody
    )

    @DELETE("/api/applicationUser/Recipe/{recipeId}")
    suspend fun deleteRecipeUser(
        @Header("Authorization") token: String, @Path("recipeId") recipeId: String
    )

    @GET("/api/recipe/{recipeId}/steps")
    suspend fun getListStepsRecipe(
        @Header("Authorization") token: String, @Path("recipeId") recipeId: String
    ): List<StepDto>

    @POST("/api/recipe/{recipeId}/steps")
    suspend fun createStepRecipe(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: String,
        @Body stepData: StepDataDto
    )

    @PUT("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun updateStepRecipe(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: String,
        @Path("stepId") stepId: String,
        @Body stepData: StepDataDto
    )

    @DELETE("/api/recipe/{recipeId}/steps/{stepId}")
    suspend fun deleteStepRecipe(
        @Header("Authorization") token: String, recipeId: String, stepId: String
    )
}