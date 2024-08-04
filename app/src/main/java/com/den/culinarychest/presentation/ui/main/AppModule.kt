package com.den.culinarychest.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import com.example.culinarychest.data.api.RetrofitInstance
import com.example.culinarychest.data.repository.ImageRepositoryImpl
import com.example.culinarychest.data.repository.RecipeRepositoryImpl
import com.example.culinarychest.data.repository.RecipeStepsRepositoryImpl
import com.example.culinarychest.data.repository.TokenRepositoryImpl
import com.example.culinarychest.data.repository.UserFavoriteRecipeRepositoryImpl
import com.example.culinarychest.data.repository.UserRecipeRepositoryImpl
import com.example.culinarychest.data.repository.UserRepositoryImpl

object AppModule {
    @SuppressLint("StaticFieldLeak")
    private lateinit var tokenRepository: TokenRepositoryImpl
    private lateinit var retrofitInstance: RetrofitInstance

    fun init(context: Context) {
        tokenRepository = TokenRepositoryImpl(context)
        retrofitInstance = RetrofitInstance(tokenRepository)
    }

    fun provideTokenRepository() = tokenRepository

    fun provideImageRepository() = ImageRepositoryImpl()
    fun provideUserRepository() = UserRepositoryImpl(retrofitInstance.culinaryChestApi)

    fun provideRecipeRepository() = RecipeRepositoryImpl(retrofitInstance.culinaryChestApi)

    fun provideUserFavoriteRecipeRepository() = UserFavoriteRecipeRepositoryImpl(retrofitInstance.culinaryChestApi)

    fun provideUserRecipeRepository() = UserRecipeRepositoryImpl(retrofitInstance.culinaryChestApi)

    fun provideRecipeStepsRepository() = RecipeStepsRepositoryImpl(retrofitInstance.culinaryChestApi)
}