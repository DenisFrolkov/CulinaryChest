package com.example.culinarychest.data.data.repository

import com.example.culinarychest.data.data.api.CulinaryChestAPI
import com.example.culinarychest.data.data.model.Mappers.toDomain
import com.example.culinarychest.data.data.model.Mappers.toDto
import com.example.culinarychest.domain.domain.model.step.CreateStep
import com.example.culinarychest.domain.domain.model.step.Step
import com.example.culinarychest.domain.domain.model.ProcessingResult
import com.example.culinarychest.domain.domain.repository.RecipeStepsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RecipeStepsRepositoryImpl(
    private val culinaryChestAPI: CulinaryChestAPI
) : RecipeStepsRepository {

    override suspend fun getRecipeSteps(
        token: String,
        recipeId: String
    ): Flow<ProcessingResult<List<Step>>> =
        flow {
            try {
                val response =
                    culinaryChestAPI.getListStepsRecipe(token, recipeId).map { it.toDomain() }
                emit(ProcessingResult.Success(response))
            } catch (e: HttpException) {
                emit(ProcessingResult.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(ProcessingResult.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    override suspend fun createRecipeStep(token: String, recipeId: String, step: CreateStep) {
        try {
            culinaryChestAPI.createStepRecipe(token, recipeId, step.toDto())
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }

    override suspend fun updateRecipeStep(
        token: String,
        recipeId: String,
        stepId: String,
        updateStep: CreateStep
    ) {
        try {
            culinaryChestAPI.updateStepRecipe(token, recipeId, stepId, updateStep.toDto())
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }

    override suspend fun deleteRecipeStep(token: String, recipeId: String, stepId: String) {
        try {
            culinaryChestAPI.deleteStepRecipe(token, recipeId, stepId)
        } catch (e: HttpException) {
            val errorMessage = "Unexpected error occurred: ${e.localizedMessage}"
            println(errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Network Error: Please check your internet connection and try again."
            println(errorMessage)
        }
    }
}